//
//  RecetaBasicaViewController.swift
//  Quiero Mas
//
//  Created by Fernando N. Frassia on 6/5/17.
//  Copyright © 2017 Fernando N. Frassia. All rights reserved.
//

import UIKit
import SDWebImage
import AVKit
import AVFoundation

class RecetaBasicaViewController: UIViewController, UITableViewDataSource, UITableViewDelegate {

    @IBOutlet weak var table: UITableView!
    @IBOutlet weak var basicaTitle: UILabel!
    @IBOutlet weak var videoImgView: UIImageView!
    
    var basicaDict: [String:Any]?
    var basicaNombre: String?
    
    //MARK: - View Will Appear
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        setBasicaTitle()
        setVideo()
    }
    
    func setBasicaTitle() {
        basicaTitle.text = basicaNombre
    }
    
    func setVideo() {
        if let thumbnail = basicaDict?[firBasicaThumbnail] as? String {
            videoImgView.sd_setImage(with: URL(string:thumbnail), placeholderImage: UIImage(named: "Thumbnail Receta"))
        }
    }
    
    //MARK: - UITableView Data Source
    func numberOfSections(in tableView: UITableView) -> Int {
        return 1
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        var rows = 0
        if let ingredientesArr = basicaDict?[firBasicaIngredientes] as? [String] {
            rows = ingredientesArr.count
        }
        return rows
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "RecetaBasicaTableViewCell", for: indexPath) as! RecetaBasicaTableViewCell
        
        if let ingredientesArr = basicaDict?[firBasicaIngredientes] as? [String] {
            cell.recetaLabel.text = ingredientesArr[indexPath.row]
        }
        
        return cell
    }
    
    
    //MARK: - UITableView Delegate
    func tableView(_ tableView: UITableView, viewForHeaderInSection section: Int) -> UIView? {
        let label = UILabel()
        label.backgroundColor = .white
        label.text = "     INGREDIENTES PARA 1 PORCIÓN"
        label.font = UIFont(name: "Cera-Bold", size: 16)
        
        return label
    }
    
    func tableView(_ tableView: UITableView, heightForHeaderInSection section: Int) -> CGFloat {
        return 40
    }
    
    //MARK: - IBAction Top
    @IBAction func closeAction(_ sender: UIButton) {
        dismiss(animated: true, completion: nil)
    }
    
    @IBAction func playVideo(_ sender: UIButton) {
        if let videoString = basicaDict?[firBasicaVideo] as? String {
            let videoURL = URL(string: videoString)
            let player = AVPlayer(url: videoURL!)
            let playerViewController = AVPlayerViewController()
            playerViewController.player = player
            self.present(playerViewController, animated: true) {
                playerViewController.player!.play()
            }
        }
    }
    
    
    
    
    
    
    
}
