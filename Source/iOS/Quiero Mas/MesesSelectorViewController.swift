//
//  MesesSelectorViewController.swift
//  Quiero Mas
//
//  Created by Fernando N. Frassia on 6/3/17.
//  Copyright © 2017 Fernando N. Frassia. All rights reserved.
//

import UIKit

class MesesSelectorViewController: UIViewController {
    
    override func viewDidLoad() {
        super.viewDidLoad()
        setAppMainColor()
    }
    
    func setAppMainColor() {
        self.view.backgroundColor = appMainColor
    }

    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        self.navigationItem.title = "El ABC de la nutrición"
        self.navigationController?.navigationBar.topItem?.title = ""
        self.navigationController?.navigationBar.tintColor = .white
        self.navigationController?.navigationBar.backgroundColor = appMainColor
        self.navigationController?.navigationBar.setBackgroundImage(UIImage(), for: .default)
        self.navigationController?.navigationBar.shadowImage = UIImage()
        self.navigationController?.navigationBar.titleTextAttributes = [NSForegroundColorAttributeName: UIColor.white, NSFontAttributeName : UIFont(name: "Cera-Bold", size: 18)!]
        self.navigationController?.navigationBar.topItem?.rightBarButtonItem?.setTitleTextAttributes([NSFontAttributeName : UIFont(name: "Cera-Regular", size: 16)!], for: .normal)
    }
    
    override func viewWillDisappear(_ animated: Bool) {
        super.viewWillDisappear(animated)
        self.navigationController?.navigationBar.topItem?.title = "El ABC de la nutrición"
    }
    
    //MARK: - IBAction
    @IBAction func mesAction(_ sender: UIButton) {
        performSegue(withIdentifier: "mesSegue", sender: sender)
    }
    
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if let b = sender as? UIButton {
            let t = b.tag
            let vc = segue.destination as! MesViewController
            vc.num = t
        }
    }
    
    
    

}
