//
//  EstimulacionVideosViewController.swift
//  Quiero Mas
//
//  Created by Fernando N. Frassia on 5/31/17.
//  Copyright © 2017 Fernando N. Frassia. All rights reserved.
//

import UIKit

class EstimulacionVideosViewController: UIViewController {
    
    var num: String?
    
    override func viewDidLoad() {
        super.viewDidLoad()
    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        if num != nil {
            self.navigationItem.title = num! + " meses"
        }
        self.navigationController?.navigationBar.tintColor = .white
    }
    
    override func viewWillDisappear(_ animated: Bool) {
        super.viewWillDisappear(animated)
        self.navigationController?.navigationBar.tintColor = .clear
    }



}
