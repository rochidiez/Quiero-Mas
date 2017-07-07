//
//  PostreViewController.swift
//  Quiero Mas
//
//  Created by Fernando N. Frassia on 6/5/17.
//  Copyright © 2017 Fernando N. Frassia. All rights reserved.
//

import UIKit

class PostreViewController: UIViewController {
    
    @IBOutlet weak var nombreLabel: UILabel!
    @IBOutlet weak var descriptionLabel: UILabel!
    
    var postreDic: [String:String]?
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        setName()
        setDescription()
    }
    
    func setName() {
        nombreLabel.text = postreDic?[firPostreNombre]
    }
    
    func setDescription() {
        descriptionLabel.text = postreDic?[firPostreDescripcion]
    }

    @IBAction func closeAction(_ sender: UIButton) {
        dismiss(animated: true, completion: nil)
    }


}
