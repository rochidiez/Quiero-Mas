//
//  ABCNutricionViewController.swift
//  Quiero Mas
//
//  Created by Fernando N. Frassia on 4/7/17.
//  Copyright © 2017 Fernando N. Frassia. All rights reserved.
//

import UIKit
import SWRevealViewController

class ABCNutricionViewController: UIViewController {
    
    @IBOutlet weak var revealMenuButton: UIBarButtonItem!
    @IBOutlet weak var table: UITableView!
    @IBOutlet weak var orangeView: UIView!
    @IBOutlet weak var forwardArrow: UIImageView!
    @IBOutlet weak var titleWeb: UIWebView!
    @IBOutlet weak var spinner: UIActivityIndicatorView!
    
    var nutricionDic: [String:Any]?
    
    override func viewDidLoad() {
        super.viewDidLoad()
        setRevealMenuButton()
        setAppMainColor()
        FirebaseAPI.getDatosNutricion()
        NotificationCenter.default.addObserver(self, selector: #selector(self.reloadNutricion), name: NSNotification.Name(rawValue: nutricionUpdated), object: nil)
    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        self.navigationController?.navigationBar.backgroundColor = appMainColor
    }
    
    override func viewWillDisappear(_ animated: Bool) {
        super.viewWillDisappear(animated)
        self.navigationController?.navigationBar.backgroundColor = .clear
    }
    
    func setRevealMenuButton() {
        revealMenuButton.target = self.revealViewController()
        revealMenuButton.action = #selector(SWRevealViewController.revealToggle(_:))
        self.view.addGestureRecognizer(self.revealViewController().panGestureRecognizer())
    }
    
    func setAppMainColor() {
        view.backgroundColor = appMainColor
        orangeView.backgroundColor = appMainColor
        forwardArrow.tintColor = appMainColor
        spinner.color = appMainColor
    }
    
    func reloadNutricion() {
        if let storedDic = UserDefaults.standard.dictionary(forKey: "nutricion") {
            nutricionDic = storedDic
        }
        
        if let d = nutricionDic {
            if d["INTRODUCCION"] != nil {
                titleWeb.loadHTMLString(d["INTRODUCCION"] as! String, baseURL: nil)
            }
        }

        spinner.stopAnimating()
    }
    
}
