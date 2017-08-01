//
//  MesViewController.swift
//  Quiero Mas
//
//  Created by Fernando N. Frassia on 6/3/17.
//  Copyright © 2017 Fernando N. Frassia. All rights reserved.
//

import UIKit

class MesViewController: UIViewController, UITableViewDataSource, UITableViewDelegate {
    
    var mesArr: [[String:String]]?
    var titulo: String?
    let tableBackgroundColor = UIColor(red: 247/255, green: 248/255, blue: 250/255, alpha: 1)
    
    @IBOutlet weak var table: UITableView!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        setNavBar()
        setAppMainColor()
    }
    
    func setNavBar() {
        self.navigationController?.navigationBar.topItem?.title = ""
        self.navigationController?.navigationBar.tintColor = .white
        self.navigationController?.navigationBar.setBackgroundImage(UIImage(), for: .default)
        self.navigationController?.navigationBar.shadowImage = UIImage()
        self.navigationController?.navigationBar.titleTextAttributes = [NSForegroundColorAttributeName: UIColor.white, NSFontAttributeName : UIFont(name: "Cera-Bold", size: 18)!]
        self.navigationController?.navigationBar.topItem?.rightBarButtonItem?.setTitleTextAttributes([NSFontAttributeName : UIFont(name: "Cera-Regular", size: 16)!], for: .normal)
    }
    
    func setAppMainColor() {
        view.backgroundColor = appMainColor
    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        if titulo != nil {
            self.navigationItem.title = titulo!
        }
    }
    
    //MARK: - UITableview Data Source
    func numberOfSections(in tableView: UITableView) -> Int {
        return 1
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        if mesArr != nil {
            return mesArr!.count
        }
        return 0
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "MesTableViewCell", for: indexPath) as! MesTableViewCell
        
        cell.topFirst.isHidden = indexPath.row != 0
        cell.leftFirst.isHidden = indexPath.row != 0
        cell.rightFirst.isHidden = indexPath.row != 0
        
        if let sectionDic = mesArr?[indexPath.row] {
            cell.title.text = sectionDic[firNutricionMesTitulo]
            
            if sectionDic[firNutricionMesStatus] == firNutricionMesStatusClosed {
                cell.openImg.image = UIImage(named: "Mas Orange")
            } else {
                cell.openImg.image = UIImage(named: "Menos Orange")
            }
            
            cell.openButton.tag = indexPath.row
            cell.openButton.addTarget(self, action: #selector(MesViewController.openCloseCell(sender:)), for: .touchUpInside)
            
            cell.orangeView.isHidden = sectionDic[firNutricionMesStatus] == firNutricionMesStatusClosed
            
            cell.web.loadHTMLString(sectionDic[firNutricionMesDescripcion]!, baseURL: nil)
        }
        
        return cell
    }
    
    
    //MARK: - UITableView Delegate
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        if let sectionDic = mesArr?[indexPath.row] {
            if sectionDic[firNutricionMesStatus] == firNutricionMesStatusClosed {
                return 80
            } else {
                return 350
            }
        }
        return 80
    }
    
    
    func openCloseCell(sender: UIButton) {
        let t = sender.tag
        if var sectionDic = mesArr?[t] {
            if sectionDic[firNutricionMesStatus] == firNutricionMesStatusClosed {
                sectionDic[firNutricionMesStatus] = firNutricionMesStatusOpened
            } else {
                sectionDic[firNutricionMesStatus] = firNutricionMesStatusClosed
            }
            mesArr?[t] = sectionDic
            table.reloadRows(at: [IndexPath(row: t, section: 0)], with: .automatic)
        }
    }
    
    

}
