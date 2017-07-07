//
//  MenuTableViewController.swift
//  Quiero Mas
//
//  Created by Fernando N. Frassia on 3/26/17.
//  Copyright © 2017 Fernando N. Frassia. All rights reserved.
//

import UIKit
import Firebase
import FirebaseStorageUI

class MenuTableViewController: UITableViewController {
    
    @IBOutlet weak var orangeHeader: UIView!
    @IBOutlet weak var nameLabel: UILabel!
    @IBOutlet weak var babyImgView: UIImageView!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        setTableView()
        setAppMainColor()
        NotificationCenter.default.addObserver(self, selector: #selector(self.reloadPerfil), name: NSNotification.Name(rawValue: perfilUpdated), object: nil)
        NotificationCenter.default.addObserver(self, selector: #selector(self.reloadPerfil), name: NSNotification.Name(rawValue: perfilLoaded), object: nil)
    }
    
    func setTableView() {
        if DeviceType.IS_IPHONE_5 {tableView.isScrollEnabled = true}
    }
    
    func setAppMainColor() {
        tableView.backgroundColor = appMainColor
        orangeHeader.backgroundColor = appMainColor
    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        setUserName()
        loadBabyImgView()
    }
    
    func setUserName() {
        if let userDic = UserDefaults.standard.dictionary(forKey: defPerfil) {
            if let datosDic = userDic[defPerfilDatos] as? [String:String] {
                nameLabel.text = datosDic[defPerfilDatosNombre]
            }
        }
    }
    
    func loadBabyImgView() {
        let user = FIRAuth.auth()?.currentUser
        guard let firebaseID = user?.uid else {return}
        
        let storage = FIRStorage.storage()
        let storageRef = storage.reference()
        let bebesRef = storageRef.child("Bebes/\(firebaseID).jpeg")
        
        babyImgView.sd_setImage(with: bebesRef, placeholderImage: UIImage(named: "Circle Baby"))
        babyImgView.layer.cornerRadius = babyImgView.frame.width/2
    }
    
    func reloadPerfil() {
        setUserName()
        loadBabyImgView()
    }
    

    // MARK: - Table view data source
    override func numberOfSections(in tableView: UITableView) -> Int {
        return 5
    }

    override func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        switch section {
        case 0:
            return 1
        case 1:
            return 2
        case 2:
            return 3
        case 3:
            return 2
        case 4:
            if DeviceType.IS_IPHONE_5 {
                return 3
            } else {
                return 7
            }
        default:
            return 0
        }
    }

    
    override func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "MenuCell", for: indexPath) as! MenuTableViewCell
        
        switch indexPath.section {
            case 0:
                cell.img.image = UIImage(named: "Inicio Menu")
                cell.name.text = "Inicio"
            case 1:
                switch indexPath.row {
                    case 0:
                        cell.img.image = UIImage(named: "Todas Menu")
                        cell.name.text = "Todas las recetas"
                    case 1:
                        cell.img.image = UIImage(named: "Plan Menu")
                        cell.name.text = "Plan de nutrición"
                    default:
                        print("")
                }
            case 2:
                switch indexPath.row {
                    case 0:
                        cell.img.image = UIImage(named: "Estimulacion Menu")
                        cell.name.text = "Estimulación y educación"
                    case 1:
                        cell.img.image = UIImage(named: "Nutricion Menu")
                        cell.name.text = "Nutrición"
                    case 2:
                        cell.img.image = UIImage(named: "Lactancia Menu")
                        cell.name.text = "Lactancia"
                    default:
                        print("")
                }
            case 3:
                switch indexPath.row {
                    case 0:
                        cell.img.image = UIImage(named: "Lista Menu")
                        cell.name.text = "Lista de compras semanal"
                    case 1:
                        cell.img.image = UIImage(named: "Favoritas Menu")
                        cell.name.text = "Recetas favoritas"
                    default:
                        print("")
                }
            case 4:
                switch indexPath.row {
                    case 0:
                        cell.img.image = UIImage(named: "Sobre Menu")
                        cell.name.text = "Sobre Quiero Más!"
                    case 1:
                        cell.img.image = UIImage(named: "Recomendar Menu")
                        cell.name.text = "Recomendar Quiero Más!"
                    case 2:
                        cell.img.image = UIImage(named: "Terminos Menu")
                        cell.name.text = "Términos y condiciones"
                    default:
                        cell.img.image = nil
                        cell.name.text = nil
                }
            default:
                print("")
        }

        return cell
    }
    
    override func tableView(_ tableView: UITableView, heightForHeaderInSection section: Int) -> CGFloat {
        if section == 0 {
            return 0
        } else {
            return 2
        }
    }
    
    override func tableView(_ tableView: UITableView, viewForHeaderInSection section: Int) -> UIView? {
        let bigView = UIView()
        bigView.backgroundColor = .white
        
        let imgView = UIImageView()
        imgView.image = UIImage(named: "Orange Separator Menu")
        imgView.frame = CGRect(x:0, y:0, width: tableView.frame.size.width/2, height: 2)
        bigView.addSubview(imgView)
        
        return bigView
    }
    
    //MARK: - Table view Delegate
    override func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        switch indexPath.section {
        case 0:
            let story = UIStoryboard(name: "Main", bundle: nil)
            let vc = story.instantiateViewController(withIdentifier: "HomeNav")
            self.revealViewController().pushFrontViewController(vc, animated: true)
        case 1:
            switch indexPath.row {
            case 0:
                let story = UIStoryboard(name: "Main", bundle: nil)
                let planVC = story.instantiateViewController(withIdentifier: "TodasNav")
                self.revealViewController().pushFrontViewController(planVC, animated: true)
            case 1:
                let story = UIStoryboard(name: "Main", bundle: nil)
                let planVC = story.instantiateViewController(withIdentifier: "PlanNav")
                self.revealViewController().pushFrontViewController(planVC, animated: true)
            default:
                print("")
            }
        case 2:
            switch indexPath.row {
            case 0:
                let story = UIStoryboard(name: "Main", bundle: nil)
                let planVC = story.instantiateViewController(withIdentifier: "EstimulacionNav")
                self.revealViewController().pushFrontViewController(planVC, animated: true)
            case 1:
                let story = UIStoryboard(name: "Main", bundle: nil)
                let planVC = story.instantiateViewController(withIdentifier: "ABCNutricionNav")
                self.revealViewController().pushFrontViewController(planVC, animated: true)
            case 2:
                let story = UIStoryboard(name: "Main", bundle: nil)
                let planVC = story.instantiateViewController(withIdentifier: "LactanciaNav")
                self.revealViewController().pushFrontViewController(planVC, animated: true)
            default:
                print("")
            }
        case 3:
            switch indexPath.row {
            case 0:
                let story = UIStoryboard(name: "Main", bundle: nil)
                let vc = story.instantiateViewController(withIdentifier: "ListaNav")
                self.revealViewController().pushFrontViewController(vc, animated: true)
            case 1:
                let story = UIStoryboard(name: "Main", bundle: nil)
                let vc = story.instantiateViewController(withIdentifier: "FavoritasNav")
                self.revealViewController().pushFrontViewController(vc, animated: true)
            default:
                print("")
            }
        case 4:
            switch indexPath.row {
            case 0:
                let story = UIStoryboard(name: "Main", bundle: nil)
                let vc = story.instantiateViewController(withIdentifier: "SobreNav")
                self.revealViewController().pushFrontViewController(vc, animated: true)
            case 1:
                let story = UIStoryboard(name: "Main", bundle: nil)
                let vc = story.instantiateViewController(withIdentifier: "RecomendarNav")
                self.revealViewController().pushFrontViewController(vc, animated: true)
            case 2:
                let story = UIStoryboard(name: "Main", bundle: nil)
                let vc = story.instantiateViewController(withIdentifier: "TerminosNav")
                self.revealViewController().pushFrontViewController(vc, animated: true)
            default:
                print("")
            }
        default:
            print("")
        }
    }
    
    
    //MARK: - IBAction
    @IBAction func perfilAction(_ sender: Any) {
        let story = UIStoryboard(name: "Main", bundle: nil)
        let vc = story.instantiateViewController(withIdentifier: "PerfilNav")
        self.revealViewController().pushFrontViewController(vc, animated: true)
    }
    

    //MARK: - Device
    enum UIUserInterfaceIdiom : Int
    {
        case Unspecified
        case Phone
        case Pad
    }
    
    struct ScreenSize
    {
        static let SCREEN_WIDTH         = UIScreen.main.bounds.size.width
        static let SCREEN_HEIGHT        = UIScreen.main.bounds.size.height
        static let SCREEN_MAX_LENGTH    = max(ScreenSize.SCREEN_WIDTH, ScreenSize.SCREEN_HEIGHT)
        static let SCREEN_MIN_LENGTH    = min(ScreenSize.SCREEN_WIDTH, ScreenSize.SCREEN_HEIGHT)
    }
    
    struct DeviceType
    {
        static let IS_IPHONE_4_OR_LESS  = UIDevice.current.userInterfaceIdiom == .phone && ScreenSize.SCREEN_MAX_LENGTH < 568.0
        static let IS_IPHONE_5          = UIDevice.current.userInterfaceIdiom == .phone && ScreenSize.SCREEN_MAX_LENGTH == 568.0
        static let IS_IPHONE_6          = UIDevice.current.userInterfaceIdiom == .phone && ScreenSize.SCREEN_MAX_LENGTH == 667.0
        static let IS_IPHONE_6P         = UIDevice.current.userInterfaceIdiom == .phone && ScreenSize.SCREEN_MAX_LENGTH == 736.0
        static let IS_IPAD              = UIDevice.current.userInterfaceIdiom == .pad && ScreenSize.SCREEN_MAX_LENGTH == 1024.0
    }
}
