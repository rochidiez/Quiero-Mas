//
//  MesesSelectorViewController.swift
//  Quiero Mas
//
//  Created by Fernando N. Frassia on 6/3/17.
//  Copyright © 2017 Fernando N. Frassia. All rights reserved.
//

import UIKit

class MesesSelectorViewController: UIViewController {
    
    var mesesDic: [String:Any]?
    
    override func viewDidLoad() {
        super.viewDidLoad()
        setAppMainColor()
        setNavBar()
    }
    
    func setAppMainColor() {
        self.view.backgroundColor = appMainColor
        self.navigationController?.navigationBar.backgroundColor = appMainColor
    }
    
    func setNavBar() {
        self.navigationController?.navigationBar.topItem?.title = ""
        self.navigationController?.navigationBar.tintColor = .white
        self.navigationController?.navigationBar.setBackgroundImage(UIImage(), for: .default)
        self.navigationController?.navigationBar.shadowImage = UIImage()
        self.navigationController?.navigationBar.titleTextAttributes = [NSForegroundColorAttributeName: UIColor.white, NSFontAttributeName : UIFont(name: "Cera-Bold", size: 18)!]
        self.navigationController?.navigationBar.topItem?.rightBarButtonItem?.setTitleTextAttributes([NSFontAttributeName : UIFont(name: "Cera-Regular", size: 16)!], for: .normal)
    }

    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        self.navigationItem.title = "El ABC de la nutrición"
    }
    
    
    //MARK: - IBAction
    @IBAction func mesAction(_ sender: UIButton) {
        performSegue(withIdentifier: "mesSegue", sender: sender)
    }
    
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if let b = sender as? UIButton {
            let vc = segue.destination as! MesViewController
            let t = b.tag
            
            var key = ""
            if t == 7 {
                key = "7 y 8 meses"
            } else {
                key = String(t) + " meses"
            }
            
            if var tempArray = mesesDic?[key] as? [[String:String]] {
                for i in 0...tempArray.count-1 {
                    tempArray[i][firNutricionMesStatus] = firNutricionMesStatusClosed
                }
                vc.mesArr = tempArray
            }
            vc.titulo = key
        }
    }

}
