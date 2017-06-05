//
//  TerminosViewController.swift
//  Quiero Mas
//
//  Created by Fernando N. Frassia on 5/15/17.
//  Copyright © 2017 Fernando N. Frassia. All rights reserved.
//

import UIKit
import SWRevealViewController

class TerminosViewController: UIViewController {

    @IBOutlet weak var revealMenuButton: UIBarButtonItem!
    @IBOutlet weak var web: UIWebView!
    @IBOutlet weak var spinner: UIActivityIndicatorView!
    
    var terminos: String?
    
    override func viewDidLoad() {
        super.viewDidLoad()
        setRevealMenuButton()
        setAppMainColor()
        FirebaseAPI.getDatosTerminos()
        NotificationCenter.default.addObserver(self, selector: #selector(self.reloadTerminos), name: NSNotification.Name(rawValue: terminosUpdated), object: nil)
    }
    
    func setRevealMenuButton() {
        revealMenuButton.target = self.revealViewController()
        revealMenuButton.action = #selector(SWRevealViewController.revealToggle(_:))
        self.view.addGestureRecognizer(self.revealViewController().panGestureRecognizer())
    }
    
    func setAppMainColor() {
        view.backgroundColor = appMainColor
        spinner.color = appMainColor
    }
    
    func reloadTerminos() {
        if let terminosString = UserDefaults.standard.string(forKey: "terminos") {
            terminos = terminosString
            web.loadHTMLString(terminos!, baseURL: nil)
            spinner.stopAnimating()
        }
    }

}
