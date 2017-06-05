//
//  SobreViewController.swift
//  Quiero Mas
//
//  Created by Fernando N. Frassia on 5/15/17.
//  Copyright © 2017 Fernando N. Frassia. All rights reserved.
//

import UIKit
import SWRevealViewController

class SobreViewController: UIViewController {

    @IBOutlet weak var revealMenuButton: UIBarButtonItem!
    @IBOutlet weak var tituloLabel: UILabel!
    @IBOutlet weak var web: UIWebView!
    @IBOutlet weak var spinner: UIActivityIndicatorView!
    
    var sobreDic: [String:String]?
    
    override func viewDidLoad() {
        super.viewDidLoad()
        setRevealMenuButton()
        setAppMainColor()
        FirebaseAPI.getDatosSobre()
        NotificationCenter.default.addObserver(self, selector: #selector(self.reloadSobre), name: NSNotification.Name(rawValue: sobreUpdated), object: nil)
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
    
    func reloadSobre() {
        if let storedDic = UserDefaults.standard.dictionary(forKey: "sobre") as? [String:String] {
            sobreDic = storedDic
            if sobreDic?["Título"] != nil {tituloLabel.text = sobreDic?["Título"]!}
            if sobreDic?["Texto"] != nil {web.loadHTMLString((sobreDic?["Texto"]!)!, baseURL: nil)}
            spinner.stopAnimating()
        }
    }
    
}
