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
        revealMenuButton.action = #selector(SWRevealViewController.revealToggle(_:))
        scroll.addGestureRecognizer(self.revealViewController().panGestureRecognizer())
    }
    
    @IBAction func mesesAction(_ sender: UIButton) {
        performSegue(withIdentifier: "mesesSegue", sender: sender)
    }
    
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if let s = sender as? UIButton {
            let vc = segue.destination as! EstimulacionVideosViewController
            vc.num = String(s.tag)
        }
    }
    
    
    

    
    
}
