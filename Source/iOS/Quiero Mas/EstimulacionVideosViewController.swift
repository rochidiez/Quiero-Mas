//
//  EstimulacionVideosViewController.swift
//  Quiero Mas
//
//  Created by Fernando N. Frassia on 5/31/17.
//  Copyright © 2017 Fernando N. Frassia. All rights reserved.
//

import UIKit
import SDWebImage
import AVKit
import AVFoundation

class EstimulacionVideosViewController: UIViewController {
    
    var num: String?
    var videosDic: [String:[String:String]]?
    
    @IBOutlet weak var s1ImageView: UIImageView!
    @IBOutlet weak var s2ImageView: UIImageView!
    @IBOutlet weak var s3ImageView: UIImageView!
    @IBOutlet weak var s4ImageView: UIImageView!
    
    override func viewDidLoad() {
        super.viewDidLoad()
    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        setNavBar()
        loadThumbnails()
    }
    
    override func viewWillDisappear(_ animated: Bool) {
        super.viewWillDisappear(animated)
        self.navigationController?.navigationBar.tintColor = .clear
    }
    
    func setNavBar() {
        if num != nil {
            self.navigationItem.title = num! + " meses"
        }
        self.navigationController?.navigationBar.tintColor = .white
    }
    
    func loadThumbnails() {
        s1ImageView.sd_setImage(with: URL(string:(videosDic?[firEstimulacionMesSemana+"1"]?[firEstimulacionMesSemanaThumbnail])!), placeholderImage: UIImage(named: "Thumbnail Receta"))
        s2ImageView.sd_setImage(with: URL(string:(videosDic?[firEstimulacionMesSemana+"2"]?[firEstimulacionMesSemanaThumbnail])!), placeholderImage: UIImage(named: "Thumbnail Receta"))
        s3ImageView.sd_setImage(with: URL(string:(videosDic?[firEstimulacionMesSemana+"3"]?[firEstimulacionMesSemanaThumbnail])!), placeholderImage: UIImage(named: "Thumbnail Receta"))
        s4ImageView.sd_setImage(with: URL(string:(videosDic?[firEstimulacionMesSemana+"4"]?[firEstimulacionMesSemanaThumbnail])!), placeholderImage: UIImage(named: "Thumbnail Receta"))
    }
    
    @IBAction func playVideo(_ sender: UIButton) {
        if let videoString = videosDic?[firEstimulacionMesSemana + String(sender.tag)]?[firEstimulacionMesSemanaVideo] {
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
