//
//  ABCNutricionViewController.swift
//  Quiero Mas
//
//  Created by Fernando N. Frassia on 4/7/17.
//  Copyright © 2017 Fernando N. Frassia. All rights reserved.
//

import UIKit
import SWRevealViewController

class ABCNutricionViewController: UIViewController, UITableViewDataSource, UITableViewDelegate {
    
    @IBOutlet weak var revealMenuButton: UIBarButtonItem!
    @IBOutlet weak var table: UITableView!
    @IBOutlet weak var orangeView: UIView!
    @IBOutlet weak var forwardArrow: UIImageView!
    @IBOutlet weak var titleWeb: UIWebView!
    @IBOutlet weak var spinner: UIActivityIndicatorView!
    
    var seccionesDic: [String:[String]]?
    var mesesDic: [String:Any]?
    
    override func viewDidLoad() {
        super.viewDidLoad()
        setRevealMenuButton()
        setAppMainColor()
        FirebaseAPI.getDatosNutricion()
        NotificationCenter.default.addObserver(self, selector: #selector(self.reloadNutricion), name: NSNotification.Name(rawValue: nutricionUpdated), object: nil)
    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        self.navigationItem.title = "El ABC de la nutrición"
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
        self.navigationController?.navigationBar.backgroundColor = appMainColor
    }
    
    func reloadNutricion() {
        if let storedDic = UserDefaults.standard.dictionary(forKey: "nutricion") {
            if let titulo = storedDic["Título"] as? String {
                titleWeb.loadHTMLString(titulo, baseURL: nil)
            }
            
            if let secciones = storedDic["Secciones"] as? [String:Any] {
                var i = 0
                var d = [String:[String]]()
                for (key, element) in secciones {
                    d[String(i)] = [key, element as! String, "closed"]
                    i += 1
                }
                seccionesDic = d
            }
            
            if let meses = storedDic["Meses"] as? [String:Any] {
                mesesDic = meses
            }
        }
        
        table.reloadData()
        spinner.stopAnimating()
    }
    
    //MARK: - UITableView Data Source
    func numberOfSections(in tableView: UITableView) -> Int {
        return 1
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        if let d = seccionesDic {
            return d.count
        }
        return 0
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "ABCTableViewCell", for: indexPath) as! ABCTableViewCell
        
        if let meaning = seccionesDic?[String(indexPath.row)] {
            if meaning[2] == "closed" {
                cell.openButton.setImage(UIImage(named: "Mas Orange"), for: .normal)
            } else {
                cell.openButton.setImage(UIImage(named: "Menos Orange"), for: .normal)
            }
            
            cell.openButton.tag = indexPath.row
            cell.openButton.addTarget(self, action: #selector(ABCNutricionViewController.openCloseCell(sender:)), for: .touchUpInside)
            
            cell.orangeView.isHidden = meaning[2] == "closed"
            
            cell.title.text = meaning[0]
            cell.web.loadHTMLString((meaning[1]), baseURL: nil)
        }
        
        return cell
    }
    
    //MARK: - UITableView Delegate
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        if seccionesDic?[String(indexPath.row)]?[2] == "closed" {
            return 80
        } else {
            return 350
        }
    }
    
    func openCloseCell(sender: UIButton) {
        let t = sender.tag
        if seccionesDic?[String(t)]?[2] == "closed" {
            seccionesDic?[String(t)]?[2] = "opened"
        } else {
            seccionesDic?[String(t)]?[2] = "closed"
        }
        table.reloadRows(at: [IndexPath(row: t, section: 0)], with: .automatic)
    }
    
    
    //MARK: - IBAction
    @IBAction func mesesAction(_ sender: UIButton) {
        performSegue(withIdentifier: "mesesSegue", sender: sender)
    }
    
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        let vc = segue.destination as! MesesSelectorViewController
        vc.mesesDic = mesesDic
    }
    
}
