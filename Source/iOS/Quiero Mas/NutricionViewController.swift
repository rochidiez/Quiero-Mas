//
//  NutricionViewController.swift
//  Quiero Mas
//
//  Created by Fernando N. Frassia on 4/7/17.
//  Copyright © 2017 Fernando N. Frassia. All rights reserved.
//

import UIKit
import SWRevealViewController

class NutricionViewController: UIViewController {

    @IBOutlet weak var revealMenuButton: UIBarButtonItem!
    @IBOutlet weak var orangeHeader: UIView!
    @IBOutlet weak var noBabyView: UIView!
    
    //MARK: - ViewDidLoad
    override func viewDidLoad() {
        super.viewDidLoad()
        setRevealMenuButton()
        setAppMainColor()
    }
    
    func setRevealMenuButton() {
        revealMenuButton.target = self.revealViewController()
        revealMenuButton.action = #selector(SWRevealViewController.revealToggle(_:))
        self.view.addGestureRecognizer(self.revealViewController().panGestureRecognizer())
    }

    func setAppMainColor() {
        orangeHeader.backgroundColor = appMainColor
    }
    
    
    //MARK: - ViewWillAppear
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        self.navigationItem.title = "Plan de nutrición"
        checkAndSetView()
    }
    
    func checkAndSetView() {
        if let userDic = UserDefaults.standard.dictionary(forKey: "perfil") as? [String:[String:String]] {
            noBabyView.isHidden = userDic["Bebé"] != nil
        }
    }
    
    
    //MARK: - IBAction
    @IBAction func listadoAction(_ sender: Any) {
        let story = UIStoryboard(name: "Main", bundle: nil)
        let vc = story.instantiateViewController(withIdentifier: "ListaNav")
        self.revealViewController().pushFrontViewController(vc, animated: true)
    }
    
    @IBAction func topRecetaAction(_ sender: Any) {
        let story = UIStoryboard(name: "Main", bundle: nil)
        let vc = story.instantiateViewController(withIdentifier: "RecetaViewController")
        self.navigationController?.pushViewController(vc, animated: true)
    }
    
    @IBAction func bottomRecetaAction(_ sender: Any) {
        let story = UIStoryboard(name: "Main", bundle: nil)
        let vc = story.instantiateViewController(withIdentifier: "RecetaViewController")
        self.navigationController?.pushViewController(vc, animated: true)
    }
    
    @IBAction func openPerfilAction(_ sender:Any) {
        let story = UIStoryboard(name: "Main", bundle: nil)
        let vc = story.instantiateViewController(withIdentifier: "PerfilNav")
        self.revealViewController().pushFrontViewController(vc, animated: true)
    }
    
    
}
