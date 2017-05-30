//
//  PerfilViewController.swift
//  Quiero Mas
//
//  Created by Fernando N. Frassia on 5/24/17.
//  Copyright © 2017 Fernando N. Frassia. All rights reserved.
//

import UIKit
import SWRevealViewController

class PerfilViewController: UIViewController {

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
        revealMenuButton.action = Selector(("revealToggle:"))
        self.view.addGestureRecognizer(self.revealViewController().panGestureRecognizer())
    }


}
