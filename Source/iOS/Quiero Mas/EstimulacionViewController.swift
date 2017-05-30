//
//  EstimulacionViewController.swift
//  Quiero Mas
//
//  Created by Fernando N. Frassia on 4/7/17.
//  Copyright © 2017 Fernando N. Frassia. All rights reserved.
//

import UIKit
import SWRevealViewController

class EstimulacionViewController: UIViewController {
    
    @IBOutlet weak var revealMenuButton: UIBarButtonItem!
    @IBOutlet weak var scroll: UIScrollView!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        setRevealMenuButton()
    }
    
    func setRevealMenuButton() {
        revealMenuButton.target = self.revealViewController()
        revealMenuButton.action = Selector(("revealToggle:"))
        scroll.addGestureRecognizer(self.revealViewController().panGestureRecognizer())
    }
    
}
