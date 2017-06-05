//
//  MesViewController.swift
//  Quiero Mas
//
//  Created by Fernando N. Frassia on 6/3/17.
//  Copyright © 2017 Fernando N. Frassia. All rights reserved.
//

import UIKit

class MesViewController: UIViewController, UITableViewDataSource, UITableViewDelegate {
    
    var mesDic: [String:Any]?
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
        if mesDic != nil {
            return (mesDic?.count)!
        }
        return 0
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "MesTableViewCell", for: indexPath) as! MesTableViewCell
        
        cell.topFirst.isHidden = indexPath.row != 0
        cell.leftFirst.isHidden = indexPath.row != 0
        cell.rightFirst.isHidden = indexPath.row != 0
        
        if let sectionArr = mesDic?[String(indexPath.row)] as? [String] {
            cell.title.text = sectionArr[0]
            
            if sectionArr[2] == "closed" {
                cell.openButton.setImage(UIImage(named: "Mas Orange"), for: .normal)
            } else {
                cell.openButton.setImage(UIImage(named: "Menos Orange"), for: .normal)
            }
            
            cell.openButton.tag = indexPath.row
            cell.openButton.addTarget(self, action: #selector(MesViewController.openCloseCell(sender:)), for: .touchUpInside)
            
            cell.orangeView.isHidden = sectionArr[2] == "closed"
            
            cell.web.loadHTMLString(sectionArr[1], baseURL: nil)
        }
        
        return cell
    }
    
    
    //MARK: - UITableView Delegate
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        if let sectionArr = mesDic?[String(indexPath.row)] as? [String] {
            if sectionArr[2] == "closed" {
                return 80
            } else {
                return 350
            }
        }
        return 80
    }
    
    
    func openCloseCell(sender: UIButton) {
        let t = sender.tag
        if var sectionArr = mesDic?[String(t)] as? [String] {
            if sectionArr[2] == "closed" {
                sectionArr[2] = "opened"
            } else {
                sectionArr[2] = "closed"
            }
            mesDic?[String(t)] = sectionArr
            table.reloadRows(at: [IndexPath(row: t, section: 0)], with: .automatic)
        }
    }
    
    

}
