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
    @IBOutlet weak var spinner: UIActivityIndicatorView!
    @IBOutlet weak var monthButton : UIButton!
    @IBOutlet weak var scrollViewHeight: NSLayoutConstraint!
    
    var estimulacionDic: [String:[String:[String:String]]]?
    
    override func viewDidLoad() {
        super.viewDidLoad()
        setRevealMenuButton()
        FirebaseAPI.getDatosEstimulacion()
        NotificationCenter.default.addObserver(self, selector: #selector(self.reloadEstimulacion), name: NSNotification.Name(rawValue: estimulacionUpdated), object: nil)
    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        self.navigationController?.navigationBar.backgroundColor = UIColor(red: 43/255, green: 74/255, blue: 124/255, alpha: 1.0)
    }
    
    override func viewDidAppear(_ animated: Bool) {
        super.viewDidAppear(animated)
        
        self.scrollViewHeight.constant = self.monthButton.frame.origin.y + self.monthButton.frame.size.height + 20
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
            if let semanaDic = estimulacionDic?[firEstimulacionMes + String(s.tag)] {
                vc.videosDic = semanaDic
            }
        }
    }
    
    func reloadEstimulacion() {
        estimulacionDic = [String:[String:[String:String]]]()
        if let storedDic = UserDefaults.standard.dictionary(forKey: firEstimulacion) as?  [String:[String:[String:String]]] {
            estimulacionDic = storedDic
        }
        spinner.stopAnimating()
    }

    
    
}
