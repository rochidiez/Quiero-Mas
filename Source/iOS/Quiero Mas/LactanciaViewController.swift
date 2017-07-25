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
    
    //Top
    @IBOutlet weak var titleTop: UILabel!
    @IBOutlet weak var descriptionTop: UILabel!
    
    let tableBackgroundColor = UIColor(red: 247/255, green: 248/255, blue: 250/255, alpha: 1)
    var lactanciaDic: [String:Any]?
    
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
        lactanciaDic = [String:Any]()
        if var storedDic = UserDefaults.standard.dictionary(forKey: firLactancia) {
            if var tablaArray = storedDic[firLactanciaTabla] as? [[String:Any]] {
                var i = 0
                while i < tablaArray.count {
                    tablaArray[i]["abierto"] = false
                    i += 1
                }
                storedDic[firLactanciaTabla] = tablaArray
            }
            lactanciaDic = storedDic
        }
        table.reloadData()
        setTitle()
        spinner.stopAnimating()
    }
    
    func setTitle() {
        if let tituloDic = lactanciaDic?[firLactanciaTitulo] as? [String:String] {
            titleTop.text = tituloDic[firLactanciaTituloTitulo]
            descriptionTop.text = tituloDic[firLactanciaTituloTexto]
        }
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
        if let tablaArr = lactanciaDic?[firLactanciaTabla] as? [[String:Any]] {
            return tablaArr.count
        } else {
            return 0
        }
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "LactanciaTableViewCell", for: indexPath) as! LactanciaTableViewCell
        
        cell.topFirst.backgroundColor = tableBackgroundColor
        cell.topFirst.isHidden = indexPath.row != 0
        cell.leftFirst.isHidden = indexPath.row != 0
        cell.rightFirst.isHidden = indexPath.row != 0
        
        let tablaArray = lactanciaDic?[firLactanciaTabla] as! [[String:Any]]
        let cellDic = tablaArray[indexPath.row]
        
        if !(cellDic["abierto"] as! Bool) {
            cell.openIcon.image = UIImage(named: "Mas Orange")
        } else {
            cell.openIcon.image = UIImage(named: "Menos Orange")
        }
        cell.openButton.tag = indexPath.row
        cell.openButton.addTarget(self, action: #selector(LactanciaViewController.openCloseCell(sender:)), for: .touchUpInside)
        
        cell.title.text = cellDic[firLactanciaTablaTitulo] as? String
        
        cell.orangeView.isHidden = !(cellDic["abierto"] as! Bool)
        
        cell.web.loadHTMLString(cellDic[firLactanciaTablaHtml] as! String, baseURL: nil)
        
        return cell
    }
    
    //MARK: - UITableView Delegate
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        if let tablaArray = lactanciaDic?[firLactanciaTabla] as? [[String:Any]] {
            if tablaArray[indexPath.row]["abierto"] as! Bool {
                return 350
            } else {
                return 80
            }
        }
        return 0
    }
    
    func openCloseCell(sender: UIButton) {
        if var tablaArray = lactanciaDic?[firLactanciaTabla] as? [[String:Any]] {
            tablaArray[sender.tag]["abierto"] = !(tablaArray[sender.tag]["abierto"] as! Bool)
            lactanciaDic?[firLactanciaTabla] = tablaArray
            table.reloadRows(at: [IndexPath(row: sender.tag, section: 0)], with: .automatic)
        }
    }
    

    
    
    
    
    
}

