//
//  CongelarViewController.swift
//  Quiero Mas
//
//  Created by Fernando N. Frassia on 7/18/17.
//  Copyright © 2017 Fernando N. Frassia. All rights reserved.
//

import UIKit

class CongelarViewController: UIViewController, UITableViewDataSource, UITableViewDelegate {
    
    @IBOutlet weak var table: UITableView!
    @IBOutlet weak var top: UIView!
    
    var data = [Int:[Int:[String:String]]]()
    let titleDic = "title"
    let descriptionDic = "description"
    let durationDic = "duration"
    let orangeDic = "orange"
    
    override func viewDidLoad() {
        super.viewDidLoad()
        setTable()
        setData()
        setTop()
    }
    
    func setTable() {
        table.register(UINib(nibName: "CongelarDuracionTableViewCell", bundle: Bundle.main), forCellReuseIdentifier: "CongelarDuracionTableViewCell")
        table.register(UINib(nibName: "CongelarDuracionHeaderTableViewCell", bundle: Bundle.main), forCellReuseIdentifier: "CongelarDuracionHeaderTableViewCell")
        table.register(UINib(nibName: "CongelarDescripcionTableViewCell", bundle: Bundle.main), forCellReuseIdentifier: "CongelarDescripcionTableViewCell")
        table.register(UINib(nibName: "CongelarDescripcionHeaderTableViewCell", bundle: Bundle.main), forCellReuseIdentifier: "CongelarDescripcionHeaderTableViewCell")
    }
    
    func setData() {
        var section0Dic = [Int:[String:String]]()
        section0Dic[0] = [titleDic:"VEGETALES", descriptionDic:"cocidos, trozados o en puré", durationDic:"6 meses"]
        section0Dic[1] = [titleDic:"FRUTAS", descriptionDic:"cocidas, trozadas o en puré", durationDic:"4 meses"]
        section0Dic[2] = [titleDic:"LÁCTEOS", descriptionDic:"o preparaciones con lácteos", durationDic:"2 meses"]
        section0Dic[3] = [titleDic:"CARNE", descriptionDic:"cruda trozada", durationDic:"3 meses"]
        section0Dic[4] = [titleDic:"CARNE", descriptionDic:"cocida", durationDic:"3 meses"]
        section0Dic[5] = [titleDic:"POLLO", descriptionDic:"crudo o cocido", durationDic:"3 meses"]
        section0Dic[6] = [titleDic:"PESCADO", descriptionDic:"crudo o cocido", durationDic:"3 meses"]
        data[0] = section0Dic
        
        let section1Dic = [Int:[String:String]]()
        data[1] = section1Dic
        
        var section2Dic = [Int:[String:String]]()
        section2Dic[0] = [titleDic:"DESCONGELAR Y CALENTAR", orangeDic:"BAÑO MARIA", descriptionDic:"Este método consiste en disponer el agua en un recipiente con la comida dentro de una olla que contenga agua caliente y esté ubicada sobre el fuego. También, se aplica para cocciones al horno, colocando el recipiente con la preparación sobre una asadera con agua en el fondo."]
        data[2] = section2Dic
        
        var section3Dic = [Int:[String:String]]()
        section3Dic[0] = [titleDic:"MÉTODOS DE COCCIÓN DE HORTALIZAS", orangeDic:"AL VAPOR", descriptionDic:"Se realiza en una olla o recipiente con una red metálica; sobre ella se ubican las hortalizas peladas y trozadas, que de esta manera no entran en contacto con el agua hirviente del fondo y conservan su sabor y valor nutricional. Ya que los nutrientes no pasan al agua de cocción, concentrándolos, en el alimento"]
        section3Dic[1] = [titleDic:"MÉTODOS DE COCCIÓN DE HORTALIZAS", orangeDic:"POR HERVIDO", descriptionDic:"Colocar sobre el fuego una cacerola con suficiente cantidad de agua sin sal. Esperar a que ésta hierva, y recién en este momento, incorporar las hortalizas peladas, cortadas en cubitos. Dejar que retome el hervor y cocinar hasta que estén tiernas."]
        data[3] = section3Dic
        
        var section4Dic = [Int:[String:String]]()
        section4Dic[0] = [titleDic:"MÉTODOS DE COCCIÓN DE CARNES", orangeDic:"A LA PLANCHA", descriptionDic:"Es una forma de cocinar los alimentos, sin agregar aceite. Se utiliza una placa especial muy caliente y sobre ella se coloca el alimento. Ideal para la cocción de carnes, pescado u hortalizas."]
        data[4] = section4Dic
        
        var section5Dic = [Int:[String:String]]()
        section5Dic[0] = [titleDic:"MÉTODOS DE COCCIÓN DE LEGUMBRES", orangeDic:"POR HERVIDO", descriptionDic:"Una vez escogidas y lavadas, se sugiere dejarlas en remojo al menos 4 horas. Pasado el tiempo, enjuagarlas y están listas para usarse. Colocar sobre el fuego una cacerola con abundante cantidad de agua sin sal. Incorporar las legumbres enteras (lentejas, arvejas). Dejar que hiervan, bajar el fuego y cocinar hasta que estén tiernas (30 minutros aprox.). Si se trata de legumbres partidas (lentejas rojas, verdes, del Puy) no deben remojarse, y se debe calcular con exactitud la cantidad de agua, ya que de otro modo podría salir una mezcla muy aguada. El volumen de agua debe duplicar la unidad de medida del peso de las legumbres."]
        data[5] = section5Dic
        
        var section6Dic = [Int:[String:String]]()
        section6Dic[0] = [titleDic:"MÉTODOS DE COCCIÓN DE FRUTAS", orangeDic:"POR HERVIDO", descriptionDic:"Pelar y descarozar las frutas (manzanas, membrillo, ciruelas). Cortar las frutas en cuartos enteras o peladas en finas rodajas. Colocar en una cacerola la fruta junto con el azúcar y el agua. Tapar y cocinar sobre fuego bajo hasta que estén bien blandas. Revolver ocasionalmente. Pisar para obtener el puré."]
        data[6] = section6Dic
    }
    
    func setTop() {
        var height: CGFloat = 0
        if DeviceType.IS_IPHONE_6P {
            height = 358
        } else if DeviceType.IS_IPHONE_6 {
            height = 392
        } else {
            height = 445
        }
        top.frame = CGRect(x: top.frame.origin.x, y: top.frame.origin.y, width: top.frame.size.width, height: height)
    }
    
    
    //MARK: - IBAction
    @IBAction func closeAction(_ sender:UIButton) {
        dismiss(animated: true, completion: nil)
    }

    
    //MARK: - UITableViewDataSource
    func numberOfSections(in tableView: UITableView) -> Int {
        return data.count
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return data[section]!.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        if indexPath.section == 0 {
            let cell = tableView.dequeueReusableCell(withIdentifier: "CongelarDuracionTableViewCell", for: indexPath) as! CongelarDuracionTableViewCell
            
            if let dicForRow = data[indexPath.section]?[indexPath.row] {
                cell.titleLabel.text = dicForRow[titleDic]!
                cell.descriptionLabel.text = dicForRow[descriptionDic]!
                cell.durationLabel.text = dicForRow[durationDic]!
            }
            
            if indexPath.row == data[indexPath.section]!.count - 1 {
                cell.separator.isHidden = true
            }
            
            return cell
        } else {
            let cell = tableView.dequeueReusableCell(withIdentifier: "CongelarDescripcionTableViewCell", for: indexPath) as! CongelarDescripcionTableViewCell
            
            if let dicForRow = data[indexPath.section]?[indexPath.row] {
                cell.orangeLabel.text = dicForRow[orangeDic]!
                cell.descriptionLabel.text = dicForRow[descriptionDic]!
            }
            
            return cell
        }
    }
    
    //MARK: - UITableViewDelegate
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        var height: CGFloat = 0
        
        if DeviceType.IS_IPHONE_6P {
            switch indexPath.section {
            case 0:
                height = 80
            case 2:
                height = 140
            case 3:
                if indexPath.row == 0 {
                    height = 160
                } else {
                    height = 140
                }
            case 4:
                height = 107
            case 5:
                height = 267
            case 6:
                height = 180
            default:
                height = 200
            }
        } else if DeviceType.IS_IPHONE_6 {
            switch indexPath.section {
            case 0:
                height = 85
            case 2:
                height = 153
            case 3:
                if indexPath.row == 0 {
                    height = 160
                } else {
                    height = 135
                }
            case 4:
                height = 123
            case 5:
                height = 279
            case 6:
                height = 180
            default:
                height = 200
            }
        } else {
            switch indexPath.section {
            case 0:
                height = 85
            case 2:
                height = 177
            case 3:
                if indexPath.row == 0 {
                    height = 183
                } else {
                    height = 160
                }
            case 4:
                height = 144
            case 5:
                height = 336
            case 6:
                height = 230
            default:
                height = 200
            }
        }
        
        return height
    }
    
    func tableView(_ tableView: UITableView, heightForHeaderInSection section: Int) -> CGFloat {
        var height: CGFloat = 0
        switch section {
        case 0:
            height = 90
        case 1:
            if DeviceType.IS_IPHONE_6P {
                height = 19
            } else if DeviceType.IS_IPHONE_6 {
                height = 16
            } else {
                height = 13
            }
        default:
            height = 50
            if DeviceType.IS_IPHONE_6 {
                if section == 3 || section == 5 {
                    height = 67
                }
            }
        }
        return height
    }
    
    func tableView(_ tableView: UITableView, viewForHeaderInSection section: Int) -> UIView? {
        switch section {
        case 0:
            return tableView.dequeueReusableCell(withIdentifier: "CongelarDuracionHeaderTableViewCell")
        case 1:
            return UIView()
        default:
            let header = tableView.dequeueReusableCell(withIdentifier: "CongelarDescripcionHeaderTableViewCell") as! CongelarDescripcionHeaderTableViewCell
            header.headerLabel.text = data[section]![0]![titleDic]
            return header
        }
    }
    
    
    
    
    
    
    
    
    
    
}
