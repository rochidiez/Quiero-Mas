//
//  ListaViewController.swift
//  Quiero Mas
//
//  Created by Fernando N. Frassia on 5/15/17.
//  Copyright © 2017 Fernando N. Frassia. All rights reserved.
//

import UIKit
import SWRevealViewController
import Firebase

class ListaViewController: UIViewController, UITableViewDataSource, UIPickerViewDataSource, UIPickerViewDelegate {

    @IBOutlet weak var revealMenuButton: UIBarButtonItem!
    @IBOutlet weak var orangeView: UIView!
    @IBOutlet weak var dateTF: UITextField!
    @IBOutlet weak var dateLabel: UILabel!
    @IBOutlet weak var picker: UIPickerView!
    @IBOutlet weak var table: UITableView!
    @IBOutlet weak var noBabyView: UIView!
    
    var ingredientes: [String]?
    
    enum ListaError: Error {
        case invalidIndex
    }
    
    //MARK: - ViewDidLoad
    override func viewDidLoad() {
        super.viewDidLoad()
        setRevealMenuButton()
        setAppMainColor()
        setDateTF()
        setTapGesture()
        setObservers()
    }
    
    func setRevealMenuButton() {
        revealMenuButton.target = self.revealViewController()
        revealMenuButton.action = #selector(SWRevealViewController.revealToggle(_:))
        self.view.addGestureRecognizer(self.revealViewController().panGestureRecognizer())
    }
    
    func setAppMainColor() {
        view.backgroundColor = appMainColor
        orangeView.backgroundColor = appMainColor
        table.backgroundColor = appMainColor
    }
    
    func setDateTF() {
        dateTF.inputView = picker
    }
    
    func setTapGesture() {
        let tapGesture = UITapGestureRecognizer(target: self, action: #selector(self.tapGestureHandler))
        self.view.addGestureRecognizer(tapGesture)
    }
    
    func tapGestureHandler() {
        self.view.endEditing(true)
    }
    
    func setObservers() {
        NotificationCenter.default.addObserver(self, selector: #selector(self.ingredientesSentAlert), name: NSNotification.Name(rawValue: ingredientesSent), object: nil)
    }
    
    
    //MARK: - ViewWillAppear
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        showHideNoBabyView()
    }
    
    func showHideNoBabyView() {
        if let userDic = UserDefaults.standard.dictionary(forKey: defPerfil) {
            noBabyView.isHidden = userDic[defPerfilBebe] != nil
            if userDic[defPerfilBebe] != nil {
                do {
                    _ = try DateManager.getBabyDayInPlan()
                    noBabyView.isHidden = true
                } catch {
                    noBabyView.isHidden = false
                }
            }
        }
    }
    
    
    //MARK: - Aux
    func ingredientesSentAlert() {
        let user = FIRAuth.auth()?.currentUser
        if let email = user?.email {
            showAlert(text: "Tu lista de compras se ha enviado exitósamente a \(email)")
        }
    }
    
    func showAlert(text: String) {
        let alert = UIAlertController(title: "",
                                      message: text,
                                      preferredStyle: UIAlertControllerStyle.alert)
        
        let cancelAction = UIAlertAction(title: "OK",
                                         style: .cancel, handler: nil)
        
        alert.addAction(cancelAction)
        present(alert, animated: true, completion: nil)
        return
    }
    
    
    //MARK: - UITableView Data Source
    func numberOfSections(in tableView: UITableView) -> Int {
        return 1
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        if ingredientes != nil {
            return ingredientes!.count
        } else {
            return 0
        }
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "ListaTableViewCell", for: indexPath) as! ListaTableViewCell
        
        cell.title.text = ingredientes?[indexPath.row]
        cell.cancelButton.tag = indexPath.row
        cell.cancelButton.addTarget(self, action: #selector(deleteIngredient(_:)), for: .touchUpInside)
        
        return cell
    }
    
    //MARK: - Delete element
    @IBAction func deleteIngredient(_ sender: UIButton) {
        ingredientes?.remove(at: sender.tag)
        table.reloadData()
    }

    //MARK: - Picker
    func numberOfComponents(in pickerView: UIPickerView) -> Int {
        return 1
    }
    
    func pickerView(_ pickerView: UIPickerView, numberOfRowsInComponent component: Int) -> Int {
        return 7
    }
    
    func pickerView(_ pickerView: UIPickerView, titleForRow row: Int, forComponent component: Int) -> String? {
        var s = ""
        switch row {
        case 0:
            s = "Lunes"
        case 1:
            s = "Martes"
        case 2:
            s = "Miércoles"
        case 3:
            s = "Jueves"
        case 4:
            s = "Viernes"
        case 5:
            s = "Sábado"
        case 6:
            s = "Domingo"
        default:
            s = ""
        }
        return s
    }
    
    func pickerView(_ pickerView: UIPickerView, didSelectRow row: Int, inComponent component: Int) {
        setDateTF(weekDay: row)
        
        var currentDay = DateManager.getCurrentDay()
        currentDay = (currentDay - 2) % 7
        var diff = 0
        var found = false
        while (!found) {
            if (currentDay + diff) % 7 == row {
                found = true
                break
            }
            diff += 1
        }
        
        setDateLabel(diff: diff)
        setTable(diff: diff)
    }
    
    func setDateTF(weekDay: Int) {
        switch weekDay {
        case 0:
            dateTF.text = "Lunes"
        case 1:
            dateTF.text = "Martes"
        case 2:
            dateTF.text = "Miércoles"
        case 3:
            dateTF.text = "Jueves"
        case 4:
            dateTF.text = "Viernes"
        case 5:
            dateTF.text = "Sábado"
        case 6:
            dateTF.text = "Domingo"
        default:
            print("")
        }
    }
    
    func setDateLabel(diff: Int) {
        let initialMarketDateSring = DateManager.getDateStringByAddingDays(date: Date(), daysToAdd: diff)
        let finalMarketDateString = DateManager.getDateStringByAddingDays(date: Date(), daysToAdd: diff+6)
        dateLabel.text = "Para recetas del \(initialMarketDateSring) al \(finalMarketDateString)"
    }
    
    func setTable(diff: Int) {
        let initialIndex: Int?
        do {
            initialIndex = try DateManager.getBabyDayInPlan() + diff
        } catch {
            ingredientes = nil
            table.reloadData()
            view.endEditing(true)
            return
        }
        
        let finalIndex = min(initialIndex!+6, 185)
        
        do {
            ingredientes = try getIngredientes(initialIndex: initialIndex!, finalIndex: finalIndex)
        } catch {
            ingredientes = nil
        }
        
        table.reloadData()
        view.endEditing(true)
    }
    
    func getIngredientes(initialIndex: Int, finalIndex: Int) throws -> [String]? {
        guard initialIndex >= 0 && finalIndex <= 185 && initialIndex < finalIndex else {
            throw ListaError.invalidIndex
        }
        
        if let recetasDic = UserDefaults.standard.dictionary(forKey: defRecetas) {
            if let rIngredientesDic = recetasDic[firIngredientes] as? [String:Any] {
                if let rEdadArr = recetasDic[firPorEdad] as? [[String:Any]] {
                    if let rDicNombre = recetasDic[firPorNombre] as? [String:Any] {
                        
                        //busco nombres de recetas
                        var names = [String]()
                        var i = initialIndex
                        while i <= finalIndex {
                            if let almuerzoDic = rEdadArr[i][firPorEdadAlmuerzo] as? [String:String] {
                                names.append(almuerzoDic[firPorEdadAlmuerzoReceta]!)
                            }
                            if let cenaDic = rEdadArr[i][firPorEdadCena] as? [String:String] {
                                names.append(cenaDic[firPorEdadCenaReceta]!)
                            }
                            i += 1
                        }
                        
                        //busco recetas y sumo cantidades
                        var cantDic = [String:Int]()
                        for name in names {
                            if let receta = rDicNombre[name] as? [String:Any] {
                                if let ingredientesDic = receta[firRecetaIngredientesLista] as? [String:Int] {
                                    for (key, element) in ingredientesDic {
                                        if cantDic[key] == nil {
                                            cantDic[key] = element
                                        } else {
                                            cantDic[key] = cantDic[key]! + element
                                        }
                                    }
                                }
                            }
                        }
                        
                        //busco nombres, cambio aridad y retorno
                        var ingredientesList = [String]()
                        for (key, element) in cantDic {
                            if let nombreDic = rIngredientesDic[key] as? [String:String] {
                                ingredientesList.append("\(element) \(nombreDic[firIngredientesNombre]!)")
                            }
                        }
                        return ingredientesList
                    }
                }
            }
        }
        
        return nil
    }

    
    //MARK: - IBAction
    @IBAction func sendEmail(_ sender: Any) {
        if ingredientes != nil {
            FirebaseAPI.sendIngredients(list: ingredientes!)
        }
    }
    
    @IBAction func openPerfilAction(_ sender: Any) {
        let story = UIStoryboard(name: "Main", bundle: nil)
        let vc = story.instantiateViewController(withIdentifier: "PerfilNav")
        self.revealViewController().pushFrontViewController(vc, animated: true)
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
