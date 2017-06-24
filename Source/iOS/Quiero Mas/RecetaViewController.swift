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
    
    var recetaDict: [String:Any]?
    var recetaNombre: String?
    
    //Bottom
    @IBOutlet weak var bottomView: UIView!
    
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
    
    //Variante
    @IBOutlet weak var varianteLabel: UILabel!
    @IBOutlet weak var varianteHeightConstraint: NSLayoutConstraint!

    
    //MARK: - View Did Load
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
    
    
    //MARK: - View Will Appear
    override func viewWillAppear(_ animated: Bool) {
        setVariante()
        setBottom()
    }
    
    func setVariante() {
        if recetaDict?["Variante"] != nil {
            varianteLabel.text = recetaDict?["Variante"] as? String
        }
    }
    
    func setBottom() {
        setVarianteView()
        bottomView.frame = CGRect(origin: bottomView.frame.origin, size: CGSize(width: bottomView.frame.size.width, height:varianteHeightConstraint.constant + 387))
    }
    
    func setVarianteView() {
        if let labelHeight = varianteLabel.text?.height(withConstrainedWidth: varianteLabel.frame.width, font: varianteLabel.font) {
            let viewHeight = varianteLabel.frame.origin.y + labelHeight + 32
            varianteHeightConstraint.constant = viewHeight
        }
    }
    
    
    //MARK: - UITableView Data Source
    func numberOfSections(in tableView: UITableView) -> Int {
        return 2
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        var sections = 0
        if section == 0 {
            if let ingredientesArr = recetaDict?["Ingredientes"] as? [[String:Any]] {
                sections = ingredientesArr.count
            }
        } else {
            if let pasosArr = recetaDict?["Pasos"] as? [String] {
                sections = pasosArr.count
            }
        }
        
        return sections
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        if indexPath.section == 0 {
            let cell = tableView.dequeueReusableCell(withIdentifier: "IngredientesTableViewCell", for: indexPath) as! IngredientesTableViewCell
            
            if let ingredientesArr = recetaDict?["Ingredientes"] as? [[String:Any]] {
            let ingredienteDic = ingredientesArr[indexPath.row]
            cell.title.text = ingredienteDic["Nombre"] as? String
            
            if ingredienteDic["Nombre Básico"] != nil {
                    cell.title.font = UIFont(name: "Cera-Bold", size: 16)
                    cell.title.textColor = appMainColor
            }
            cell.button.isHidden = ingredienteDic["Nombre Básico"] == nil
                
            }
            
            return cell
        } else {
            let cell = tableView.dequeueReusableCell(withIdentifier: "PasosTableViewCell", for: indexPath) as! PasosTableViewCell
            
            cell.numberLabel.text = String(indexPath.row + 1)
            if let pasosArr = recetaDict?["Pasos"] as? [String] {
                cell.title.text = pasosArr[indexPath.row]
            }
            
            return cell
        }
    }
    
    
    //MARK: - UITableView Delegate
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        var height = CGFloat(0)
        if indexPath.section == 0 {
            height = CGFloat(44)
        } else {
            let screenWidth = ScreenSize.SCREEN_WIDTH
            var linesCount = CGFloat(0)
            var amountOfRows = 0
            if let pasosArr = recetaDict?["Pasos"] as? [String] {
                linesCount = CGFloat(pasosArr[indexPath.row].characters.count)
                amountOfRows = pasosArr.count
            }
            var magicValue = CGFloat(0)
            if DeviceType.IS_IPHONE_6P {
                linesCount = linesCount / 48
                magicValue = 0.07
            } else if DeviceType.IS_IPHONE_6 {
                
            } else if DeviceType.IS_IPHONE_5 {
                
            }
            height = linesCount * (screenWidth - 60) * magicValue
            
            if indexPath.row == amountOfRows - 1 {
                height += 10
            }
        }
        
        return height
    }
    
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
            if let ingredientesArr = recetaDict?["Ingredientes"] as? [[String:Any]] {
                let ingredienteDic = ingredientesArr[indexPath.row]
                if ingredienteDic["Nombre Básico"] != nil {
                    performSegue(withIdentifier: "recetaBasicaSegue", sender: ingredienteDic["Nombre Básico"])
                }
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
    
    
    //MARK: - Segue
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if segue.identifier == "tipDesarrolloSegue" {
            let vc = segue.destination as! TipDesarrolloViewController
            if let texto = recetaDict?["Tip Desarrollo"] as? String {
                vc.texto = texto
            }
        }
        
        if segue.identifier == "tipNutricionalSegue" {
            let vc = segue.destination as! TipNutricionalViewController
            if let texto = recetaDict?["Tip Nutricional"] as? String {
                vc.texto = texto
            }
        }
        
        if segue.identifier == "recetaBasicaSegue" {
            let vc = segue.destination as! RecetaBasicaViewController
            if let basicaName = sender as? String {
                if let basicasDict = UserDefaults.standard.dictionary(forKey: "recetas basicas") {
                    vc.basicaNombre = basicaName
                    vc.basicaDict = basicasDict[basicaName] as? [String:Any]
                }
            }
        }
    }
}
