//
//  TipNutricionalViewController.swift
//  Quiero Mas
//
//  Created by Fernando N. Frassia on 6/5/17.
//  Copyright © 2017 Fernando N. Frassia. All rights reserved.
//

import UIKit

class TipNutricionalViewController: UIViewController {
    
    @IBOutlet weak var textoLabel: UILabel!
    
    var texto: String?
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        textoLabel.text = texto
    }

    @IBAction func closeAction(_ sender: UIButton) {
        dismiss(animated: true, completion: nil)
    }

}
