//
//  LactanciaViewController.swift
//  Quiero Mas
//
//  Created by Fernando N. Frassia on 4/7/17.
//  Copyright © 2017 Fernando N. Frassia. All rights reserved.
//

import UIKit
import SWRevealViewController

class LactanciaViewController: UIViewController, UITableViewDataSource, UITableViewDelegate {
    
    @IBOutlet weak var revealMenuButton: UIBarButtonItem!
    @IBOutlet weak var table: UITableView!
    @IBOutlet weak var spinner: UIActivityIndicatorView!
    
    let tableBackgroundColor = UIColor(red: 247/255, green: 248/255, blue: 250/255, alpha: 1)
    var lactanciaDic = Dictionary<String,[String]>()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        setRevealMenuButton()
        setAppMainColor()
        FirebaseAPI.getDatosLactancia()
        NotificationCenter.default.addObserver(self, selector: #selector(self.reloadLactancia), name: NSNotification.Name(rawValue: lactanciaUpdated), object: nil)
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
    
    func setTableBackgroundColor() {
        table.backgroundColor = tableBackgroundColor
    }
    
    func reloadLactancia() {
        lactanciaDic = Dictionary<String,[String]>()
        if let storedDic = UserDefaults.standard.dictionary(forKey: "lactancia") {
            var i = 0
            for (key, element) in storedDic {
                lactanciaDic[String(i)] = [key, element as! String, "closed"]
                i += 1
            }
        }
        table.reloadData()
        spinner.stopAnimating()
    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        self.navigationController?.navigationBar.backgroundColor = appMainColor
        spinner.startAnimating()
    }
    
    override func viewWillDisappear(_ animated: Bool) {
        super.viewWillDisappear(animated)
        self.navigationController?.navigationBar.backgroundColor = .clear
    }
    
    //MARK: - UITableView Data Source
    func numberOfSections(in tableView: UITableView) -> Int {
        return 1
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return lactanciaDic.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "LactanciaTableViewCell", for: indexPath) as! LactanciaTableViewCell
        
        cell.topFirst.backgroundColor = tableBackgroundColor
        cell.topFirst.isHidden = indexPath.row != 0
        cell.leftFirst.isHidden = indexPath.row != 0
        cell.rightFirst.isHidden = indexPath.row != 0
        
        if lactanciaDic[String(indexPath.row)]?[2] == "closed" {
            cell.openButton.setImage(UIImage(named: "Mas Orange"), for: .normal)
        } else {
            cell.openButton.setImage(UIImage(named: "Menos Orange"), for: .normal)
        }
        cell.openButton.tag = indexPath.row
        cell.openButton.addTarget(self, action: #selector(LactanciaViewController.openCloseCell(sender:)), for: .touchUpInside)
        
        cell.title.text = lactanciaDic[String(indexPath.row)]?[0]
        
        cell.orangeView.isHidden = lactanciaDic[String(indexPath.row)]?[2] == "closed"
        
        cell.web.loadHTMLString((lactanciaDic[String(indexPath.row)]?[1])!, baseURL: nil)
        
        return cell
    }
    
    //MARK: - UITableView Delegate
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        let meaning = lactanciaDic[String(indexPath.row)]
        if let status = meaning?[2] {
            if status == "closed" {
                return 80
            } else {
                return 350
            }
        }
        return 0
    }
    
    func openCloseCell(sender: UIButton) {
        let t = sender.tag
        if lactanciaDic[String(t)]?[2] == "closed" {
            lactanciaDic[String(t)]?[2] = "opened"
        } else {
            lactanciaDic[String(t)]?[2] = "closed"
        }
        table.reloadRows(at: [IndexPath(row: t, section: 0)], with: .automatic)
    }
    

    
    
    
    
    
    
    
    
    
}

