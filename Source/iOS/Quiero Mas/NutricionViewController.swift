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
    
    override func viewDidLoad() {
        super.viewDidLoad()

        setRevealMenuButton()
        setAppMainColor()
    }

    func setAppMainColor() {
        orangeHeader.backgroundColor = appMainColor
    }

    func setRevealMenuButton() {
        revealMenuButton.target = self.revealViewController()
        revealMenuButton.action = #selector(SWRevealViewController.revealToggle(_:))
        self.view.addGestureRecognizer(self.revealViewController().panGestureRecognizer())
    }
    
    //MARK: - IBAction
    @IBAction func listadoAction(_ sender: Any) {
        let story = UIStoryboard(name: "Main", bundle: nil)
        let vc = story.instantiateViewController(withIdentifier: "ListaNav")
        self.revealViewController().pushFrontViewController(vc, animated: true)
    }
    
    
}
