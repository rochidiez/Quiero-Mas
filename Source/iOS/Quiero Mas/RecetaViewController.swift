//
//  RecetaViewController.swift
//  Quiero Mas
//
//  Created by Fernando N. Frassia on 6/5/17.
//  Copyright © 2017 Fernando N. Frassia. All rights reserved.
//

import UIKit

class RecetaViewController: UIViewController, UITableViewDataSource, UITableViewDelegate {
    
    @IBOutlet weak var table: UITableView!
    
    //Bottom
    @IBOutlet weak var firstImg: UIImageView!
    @IBOutlet weak var secondImg: UIImageView!
    @IBOutlet weak var thirdImg: UIImageView!
    @IBOutlet weak var fourthImg: UIImageView!
    @IBOutlet weak var fifthImg: UIImageView!
    
    @IBOutlet weak var firstLabel: UILabel!
    @IBOutlet weak var secondLabel: UILabel!
    @IBOutlet weak var thirdLabel: UILabel!
    @IBOutlet weak var fourthLabel: UILabel!
    @IBOutlet weak var fifthLabel: UILabel!

    override func viewDidLoad() {
        super.viewDidLoad()
        setNavBar()
        setTable()
    }

    func setNavBar() {
        self.navigationController?.navigationBar.topItem?.title = ""
        self.navigationController?.navigationBar.tintColor = .white
    }
    
    func setTable() {
        table.register(UINib(nibName: "IngredientesTableViewCell", bundle: Bundle.main), forCellReuseIdentifier: "IngredientesTableViewCell")
        table.register(UINib(nibName: "PasosTableViewCell", bundle: Bundle.main), forCellReuseIdentifier: "PasosTableViewCell")
    }
    
    
    //MARK: - UITableView Data Source
    func numberOfSections(in tableView: UITableView) -> Int {
        return 2
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return 3
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        if indexPath.section == 0 {
            let cell = tableView.dequeueReusableCell(withIdentifier: "IngredientesTableViewCell", for: indexPath) as! IngredientesTableViewCell
            
            cell.button.isHidden = indexPath.row != 0
            if indexPath.row == 0 {
                cell.title.font = UIFont(name: "Cera-Bold", size: 16)
                cell.title.textColor = appMainColor
            }
            
            return cell
        } else {
            let cell = tableView.dequeueReusableCell(withIdentifier: "PasosTableViewCell", for: indexPath) as! PasosTableViewCell
            
            cell.numberLabel.text = String(indexPath.row + 1)
            
            return cell
        }
    }
    
    
    //MARK: - UITableView Delegate
    func tableView(_ tableView: UITableView, viewForHeaderInSection section: Int) -> UIView? {
        var text = ""
        if section == 0 {
            text = "     INGREDIENTES PARA 1 PORCIÓN"
        } else {
            text = "     PASO A PASO"
        }
        
        let label = UILabel()
        label.backgroundColor = .white
        label.text = text
        label.font = UIFont(name: "Cera-Bold", size: 16)
        
        return label
    }
    
    func tableView(_ tableView: UITableView, heightForHeaderInSection section: Int) -> CGFloat {
        return 40
    }
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        if indexPath.section == 0 {
            if indexPath.row == 0 {
                performSegue(withIdentifier: "recetaBasicaSegue", sender: self)
            }
        }
    }
    
    
    //MARK: - IBAction Top
    @IBAction func tipNutricionalAction(_ sender: UIButton) {
        performSegue(withIdentifier: "tipNutricionalSegue", sender: self)
    }
    
    
    //MARK: - IBAction Bottom
    @IBAction func postreAction(_ sender: UIButton) {
        performSegue(withIdentifier: "postreSegue", sender: self)
    }
    
    @IBAction func tipDesarrolloAction(_ sender: UIButton) {
        performSegue(withIdentifier: "tipDesarrolloSegue", sender: self)
    }
    
    @IBAction func firstAction(_ sender: UIButton) {
        firstImg.image = UIImage(named: "Oval Lleno")
        firstLabel.textColor = .white
        
        secondImg.image = UIImage(named: "Oval Vacio")
        secondLabel.textColor = appMainColor
        thirdImg.image = UIImage(named: "Oval Vacio")
        thirdLabel.textColor = appMainColor
        fourthImg.image = UIImage(named: "Oval Vacio")
        fourthLabel.textColor = appMainColor
        fifthImg.image = UIImage(named: "Oval Vacio")
        fifthLabel.textColor = appMainColor
    }
    
    @IBAction func secondAction(_ sender: UIButton) {
        secondImg.image = UIImage(named: "Oval Lleno")
        secondLabel.textColor = .white
        
        firstImg.image = UIImage(named: "Oval Vacio")
        firstLabel.textColor = appMainColor
        thirdImg.image = UIImage(named: "Oval Vacio")
        thirdLabel.textColor = appMainColor
        fourthImg.image = UIImage(named: "Oval Vacio")
        fourthLabel.textColor = appMainColor
        fifthImg.image = UIImage(named: "Oval Vacio")
        fifthLabel.textColor = appMainColor
    }
    
    @IBAction func thirdAction(_ sender: UIButton) {
        thirdImg.image = UIImage(named: "Oval Lleno")
        thirdLabel.textColor = .white
        
        firstImg.image = UIImage(named: "Oval Vacio")
        firstLabel.textColor = appMainColor
        secondImg.image = UIImage(named: "Oval Vacio")
        secondLabel.textColor = appMainColor
        fourthImg.image = UIImage(named: "Oval Vacio")
        fourthLabel.textColor = appMainColor
        fifthImg.image = UIImage(named: "Oval Vacio")
        fifthLabel.textColor = appMainColor
    }
    
    @IBAction func fourthAction(_ sender: UIButton) {
        fourthImg.image = UIImage(named: "Oval Lleno")
        fourthLabel.textColor = .white
        
        firstImg.image = UIImage(named: "Oval Vacio")
        firstLabel.textColor = appMainColor
        secondImg.image = UIImage(named: "Oval Vacio")
        secondLabel.textColor = appMainColor
        thirdImg.image = UIImage(named: "Oval Vacio")
        thirdLabel.textColor = appMainColor
        fifthImg.image = UIImage(named: "Oval Vacio")
        fifthLabel.textColor = appMainColor
    }
    
    @IBAction func fifthAction(_ sender: UIButton) {
        fifthImg.image = UIImage(named: "Oval Lleno")
        fifthLabel.textColor = .white
        
        firstImg.image = UIImage(named: "Oval Vacio")
        firstLabel.textColor = appMainColor
        secondImg.image = UIImage(named: "Oval Vacio")
        secondLabel.textColor = appMainColor
        thirdImg.image = UIImage(named: "Oval Vacio")
        thirdLabel.textColor = appMainColor
        fourthImg.image = UIImage(named: "Oval Vacio")
        fourthLabel.textColor = appMainColor
    }
    
}
