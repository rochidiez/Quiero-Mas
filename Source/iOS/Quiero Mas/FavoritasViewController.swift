//
//  FavoritasViewController.swift
//  Quiero Mas
//
//  Created by Fernando N. Frassia on 5/15/17.
//  Copyright © 2017 Fernando N. Frassia. All rights reserved.
//

import UIKit
import SWRevealViewController

class FavoritasViewController: UIViewController, UITableViewDataSource, UITableViewDelegate {

    @IBOutlet weak var revealMenuButton: UIBarButtonItem!
    @IBOutlet weak var table: UITableView!
    @IBOutlet weak var noFavoritesView: UIView!
    
    var favRecetas: [[String:Any]?]?
    
    override func viewDidLoad() {
        super.viewDidLoad()
        setRevealMenuButton()
        setAppMainColor()
        NotificationCenter.default.addObserver(self, selector: #selector(self.reloadFavoritos), name: NSNotification.Name(rawValue: perfilUpdated), object: nil)
    }
    
    func setRevealMenuButton() {
        revealMenuButton.target = self.revealViewController()
        revealMenuButton.action = #selector(SWRevealViewController.revealToggle(_:))
        self.view.addGestureRecognizer(self.revealViewController().panGestureRecognizer())
    }
    
    func setAppMainColor() {
        view.backgroundColor = appMainColor
    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        self.navigationItem.title = "Recetas favoritas"
        reloadFavoritos()
    }
    
    func reloadFavoritos() {
        if let perfilDic = UserDefaults.standard.value(forKey: defPerfil) as? [String:Any] {
            if let favoritosArr = perfilDic[firUsuarioFavoritos] as? [String] {
                favRecetas = FirebaseAPI.getRecetasByNames(names: favoritosArr)
                table.reloadData()
                noFavoritesView.isHidden = true
            } else {
                noFavoritesView.isHidden = false
            }
        }
    }
    
    //MARK: - UITableView Data Source
    func numberOfSections(in tableView: UITableView) -> Int {
        return 1
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        if favRecetas != nil {
            return favRecetas!.count
        }
        return 0
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "FavoritasTableViewCell", for: indexPath) as! FavoritasTableViewCell
        
        let receta = favRecetas![indexPath.row]
        guard receta != nil else {return cell}
        
        if let n = receta?[firRecetaNombre] as? String {
            cell.favLabel.text = n
        }
        if let thumbnail = receta?[firRecetaThumbnail] as? String {
            cell.favImg.sd_setImage(with: URL(string: thumbnail), placeholderImage: UIImage(named: "Thumbnail Receta"))
        }
        
        cell.favBtn.tag = indexPath.row
        cell.favBtn.addTarget(self, action: #selector(unfavAction(_:)), for: .touchUpInside)
        
        return cell
    }
    
    //MARK: - UITableView Delegate
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        let receta = favRecetas![indexPath.row]
        guard receta != nil else {return}
        
        let story = UIStoryboard(name: "Main", bundle: nil)
        let vc = story.instantiateViewController(withIdentifier: "RecetaViewController") as! RecetaViewController
        vc.recetaNombre = receta![firRecetaNombre] as? String
        vc.recetaDict = receta!
        self.navigationController?.pushViewController(vc, animated: true)
    }
    
    //MARK: - IBAction
    @IBAction func unfavAction(_ sender: UIButton) {
        let receta = favRecetas![sender.tag]
        guard receta != nil else {return}
        
        FirebaseAPI.favUnfavReceta(name: receta![firRecetaNombre] as! String)
        favRecetas!.remove(at: sender.tag)
        table.reloadData()
        noFavoritesView.isHidden = favRecetas!.count != 0
    }
    
    @IBAction func openTodas(_ sender: UIButton) {
        let story = UIStoryboard(name: "Main", bundle: nil)
        let vc = story.instantiateViewController(withIdentifier: "TodasNav")
        self.revealViewController().pushFrontViewController(vc, animated: true)
    }
    
    

}
