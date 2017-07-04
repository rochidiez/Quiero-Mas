//
//  NutricionViewController.swift
//  Quiero Mas
//
//  Created by Fernando N. Frassia on 4/7/17.
//  Copyright © 2017 Fernando N. Frassia. All rights reserved.
//

import UIKit
import SWRevealViewController

class NutricionViewController: UIViewController {

    @IBOutlet weak var revealMenuButton: UIBarButtonItem!
    @IBOutlet weak var orangeHeader: UIView!
    @IBOutlet weak var noBabyView: UIView!
    
    //Top
    @IBOutlet weak var babyNameLabel: UILabel!
    @IBOutlet weak var babyAgeLabel: UILabel!
    
    //Dias
    @IBOutlet weak var lunesBtn: UIButton!
    @IBOutlet weak var martesBtn: UIButton!
    @IBOutlet weak var miercolesBtn: UIButton!
    @IBOutlet weak var juevesBtn: UIButton!
    @IBOutlet weak var viernesBtn: UIButton!
    @IBOutlet weak var sabadoBtn: UIButton!
    @IBOutlet weak var domingoBtn: UIButton!
    
    //Recetas
    @IBOutlet weak var almuerzoRecetaLabel: UILabel!
    @IBOutlet weak var almuerzoRecetaImg: UIImageView!
    
    @IBOutlet weak var cenaRecetaLabel: UILabel!
    @IBOutlet weak var cenaRecetaImg: UIImageView!
    
    
    //Spinner
    @IBOutlet weak var spinner: UIActivityIndicatorView!
    
    var recetasEdadDic: [String:[String:[String:[String:Any]]]]?
    var recetasNombreDic: [String:Any]?
    var showingDate: QuieroDate?
    var recetasActuales: RecetasNutricion?
    var birthdayString: String?
    var selectedDayOfTheWeek: Int?
    
    let diaSelectedColor: UIColor = .black
    let diaSelectedFont = UIFont(name: "Cera-Bold", size: 17)!
    let diaUnselectedColor = UIColor(red: 155/255, green: 155/255, blue: 155/255, alpha: 1.0)
    let diaUnselectedFont = UIFont(name: "Cera-Regular", size: 17)!
    
    
    struct RecetasNutricion {
        var almuerzoReceta: [String:Any]?
        var almuerzoPostre: [String:Any]?
        var cenaReceta: [String:Any]?
        var cenaPostre: [String:Any]?
    }
    
    //MARK: - ViewDidLoad
    override func viewDidLoad() {
        super.viewDidLoad()
        setRevealMenuButton()
        setAppMainColor()
    }
    
    func setRevealMenuButton() {
        revealMenuButton.target = self.revealViewController()
        revealMenuButton.action = #selector(SWRevealViewController.revealToggle(_:))
        self.view.addGestureRecognizer(self.revealViewController().panGestureRecognizer())
    }

    func setAppMainColor() {
        orangeHeader.backgroundColor = appMainColor
    }
    
    
    //MARK: - ViewWillAppear
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        self.navigationItem.title = "Plan de nutrición"
        checkForBaby()
        reloadNutricion()
        NotificationCenter.default.addObserver(self, selector: #selector(self.reloadNutricion), name: NSNotification.Name(rawValue: recetasUpdated), object: nil)
    }
    
    func checkForBaby() {
        if let userDic = UserDefaults.standard.dictionary(forKey: defPerfil) as? [String:[String:String]] {
            noBabyView.isHidden = userDic[defPerfilBebe] != nil
        }
    }
    
    func reloadNutricion() {
        if let recetasDic = UserDefaults.standard.dictionary(forKey: defRecetas) as? [String:[String:Any]] {
            if let rDicEdad = recetasDic[firPorEdad] as? [String:[String:[String:[String:Any]]]] {
                if let rDicNombre = recetasDic[firPorNombre] {
                    recetasEdadDic = rDicEdad
                    recetasNombreDic = rDicNombre
                    loadUIData()
                    spinner.stopAnimating()
                }
            }
        }
    }
    
    
    //MARK: - Load UI Data
    func loadUIData() {
        loadCurrentDay()
        loadShowingDate()
        loadBabyName()
        loadBabyAge()
        loadRecetaDayWeek()
    }
    
    func loadCurrentDay() {
        let day = DateManager.getCurrentDay()
        switch day {
        case 2:
            selectedDayOfTheWeek = 1
            selectLunes()
        case 3:
            selectedDayOfTheWeek = 2
            selectMartes()
        case 4:
            selectedDayOfTheWeek = 3
            selectMiercoles()
        case 5:
            selectedDayOfTheWeek = 4
            selectJueves()
        case 6:
            selectedDayOfTheWeek = 5
            selectViernes()
        case 7:
            selectedDayOfTheWeek = 6
            selectSabado()
        case 1:
            selectedDayOfTheWeek = 7
            selectDomingo()
        default:
            print("mala fecha")
        }
    }
    
    func loadShowingDate() {
        if let userDic = UserDefaults.standard.dictionary(forKey: defPerfil) as? [String:[String:String]] {
            if let bebeDic = userDic[defPerfilBebe]  {
                if let dateString = bebeDic[defPerfilBebeFechaDeNacimiento] {
                    birthdayString = dateString
                    showingDate = DateManager.getBabyAgeTuple(birthday: birthdayString!, currentDate: Date())
                }
            }
        }
    }
    
    func loadBabyName() {
        if let userDic = UserDefaults.standard.dictionary(forKey: defPerfil) as? [String:[String:String]] {
            if let bebeDic = userDic[defPerfilBebe]  {
                if bebeDic[defPerfilBebeNombre] != nil {
                    babyNameLabel.text = bebeDic[defPerfilBebeNombre]
                }
            }
        }
    }
    
    func loadBabyAge() {
        if let userDic = UserDefaults.standard.dictionary(forKey: defPerfil) as? [String:[String:String]] {
            if let bebeDic = userDic[defPerfilBebe]  {
                if let dateString = bebeDic[defPerfilBebeFechaDeNacimiento] {
                    babyAgeLabel.text = DateManager.getBabyAgeString(birthday: dateString, currentDate: Date())
                }
            }
        }
    }
    
    func loadRecetaDayWeek() {
        guard showingDate != nil else {return}
        loadRecetasActuales()
        loadRecetasUI()
    }
    
    
    //MARK: - Aux
    func loadRecetasActuales() {
        if let semanasDic = recetasEdadDic?[firPorEdadMes + " \(showingDate?.mes ?? 6)"] {
            if let diasDic = semanasDic[firPorEdadMesSemana + " \(showingDate?.semana ?? 3)"] {
                if let diaDic = diasDic[firPorEdadMesSemanaDia + " \(showingDate?.dia ?? 6)"] {
                    if let almuerzoDic = diaDic[firPorEdadMesSemanaDiaAlmuerzo] as? [String:String] {
                        if let cenaDic = diaDic[firPorEdadMesSemanaDiaCena] as? [String:String] {
                            let almuerzoReceta = FirebaseAPI.getRecetaByName(name: almuerzoDic["receta"]!)
                            let almuerzoPostre = FirebaseAPI.getPostreByName(name: almuerzoDic["postre"]!)
                            let cenaReceta = FirebaseAPI.getRecetaByName(name: cenaDic["receta"]!)
                            let cenaPostre = FirebaseAPI.getPostreByName(name: cenaDic["postre"]!)
                            recetasActuales = RecetasNutricion(almuerzoReceta: almuerzoReceta,
                                            almuerzoPostre: almuerzoPostre,
                                            cenaReceta: cenaReceta,
                                            cenaPostre: cenaPostre)
                        }
                    }
                }
            }
        }
    }
    
    func loadRecetasUI() {
        almuerzoRecetaLabel.text = recetasActuales?.almuerzoReceta?["nombre"] as? String
        almuerzoRecetaImg.sd_setImage(with: URL(string:recetasActuales?.almuerzoReceta?["thumbnail"] as! String), placeholderImage: UIImage(named: "Thumbnail Receta"))
        
        cenaRecetaLabel.text = recetasActuales?.cenaReceta?["nombre"] as? String
        cenaRecetaImg.sd_setImage(with: URL(string:recetasActuales?.cenaReceta?["thumbnail"] as! String), placeholderImage: UIImage(named: "Thumbnail Receta"))
    }
    
    func changeCurrentDay(weekDay: Int) {
        let daysDiff = weekDay - selectedDayOfTheWeek!
        selectedDayOfTheWeek = weekDay
        changePlanDateBy(dia: daysDiff)
        loadRecetaDayWeek()
    }
    
    func changePlanDateBy(dia: Int) {
        var newdia = (showingDate?.dia)! + dia
        var newSemana = (showingDate?.semana)!
        
        if newdia < 1 {
            newSemana -= 1
            newdia += 7
        }
        if newdia > 7 {
            newSemana += 1
            newdia -= 7
        }
        
        if newSemana < 1 {
            showingDate?.mes = (showingDate?.mes)! - 1
            newSemana += 5
        }
        if newSemana > 5 {
            showingDate?.mes = (showingDate?.mes)! + 1
            newSemana -= 5
        }
        
        showingDate?.dia = newdia
        showingDate?.semana = newSemana
    }
    
    func aumentarSemana(cambio: Int) {
        var newSemana = (showingDate?.semana)! + cambio
        if newSemana < 1 {
            showingDate?.mes = (showingDate?.mes)! - 1
            newSemana += 5
        }
        if newSemana > 5 {
            showingDate?.mes = (showingDate?.mes)! + 1
            newSemana -= 5
        }
        
        showingDate?.semana = newSemana
        loadRecetaDayWeek()
    }
    
    //MARK: - IBAction
    @IBAction func listadoAction(_ sender: Any) {
        let story = UIStoryboard(name: "Main", bundle: nil)
        let vc = story.instantiateViewController(withIdentifier: "ListaNav")
        self.revealViewController().pushFrontViewController(vc, animated: true)
    }
    
    @IBAction func topRecetaAction(_ sender: Any) {
        let story = UIStoryboard(name: "Main", bundle: nil)
        let vc = story.instantiateViewController(withIdentifier: "RecetaViewController") as! RecetaViewController
        vc.recetaNombre = recetasActuales?.almuerzoReceta?["nombre"] as? String
        vc.recetaDict = recetasActuales?.almuerzoReceta
        self.navigationController?.pushViewController(vc, animated: true)
    }
    
    @IBAction func bottomRecetaAction(_ sender: Any) {
        let story = UIStoryboard(name: "Main", bundle: nil)
        let vc = story.instantiateViewController(withIdentifier: "RecetaViewController") as! RecetaViewController
        vc.recetaNombre = recetasActuales?.cenaReceta?["nombre"] as? String
        vc.recetaDict = recetasActuales?.cenaReceta
        self.navigationController?.pushViewController(vc, animated: true)
    }
    
    @IBAction func openPerfilAction(_ sender:Any) {
        let story = UIStoryboard(name: "Main", bundle: nil)
        let vc = story.instantiateViewController(withIdentifier: "PerfilNav")
        self.revealViewController().pushFrontViewController(vc, animated: true)
    }
    
    @IBAction func semanaAnterior(_ sender:Any) {
        aumentarSemana(cambio: -1)
    }
    
    @IBAction func semanaProxima(_ sender:Any) {
        aumentarSemana(cambio: 1)
    }
    
    //MARK: -
    @IBAction func lunesAction(_ sender:Any) {
        changeCurrentDay(weekDay: 1)
        selectLunes()
    }
    
    @IBAction func martesAction(_ sender:Any) {
        changeCurrentDay(weekDay: 2)
        selectMartes()
    }
    
    @IBAction func miercolesAction(_ sender:Any) {
        changeCurrentDay(weekDay: 3)
        selectMiercoles()
    }
    
    @IBAction func juevesAction(_ sender:Any) {
        changeCurrentDay(weekDay: 4)
        selectJueves()
    }
    
    @IBAction func viernesAction(_ sender:Any) {
        changeCurrentDay(weekDay: 5)
        selectViernes()
    }
    
    @IBAction func sabadoAction(_ sender:Any) {
        changeCurrentDay(weekDay: 6)
        selectSabado()
    }
    
    @IBAction func domingoAction(_ sender:Any) {
        changeCurrentDay(weekDay: 7)
        selectDomingo()
    }
    
    func selectLunes() {
        lunesBtn.setTitleColor(diaSelectedColor, for: .normal)
        lunesBtn.titleLabel?.font = diaSelectedFont
        
        martesBtn.setTitleColor(diaUnselectedColor, for: .normal)
        miercolesBtn.setTitleColor(diaUnselectedColor, for: .normal)
        juevesBtn.setTitleColor(diaUnselectedColor, for: .normal)
        viernesBtn.setTitleColor(diaUnselectedColor, for: .normal)
        sabadoBtn.setTitleColor(diaUnselectedColor, for: .normal)
        domingoBtn.setTitleColor(diaUnselectedColor, for: .normal)
        
        martesBtn.titleLabel?.font  = diaUnselectedFont
        miercolesBtn.titleLabel?.font  = diaUnselectedFont
        juevesBtn.titleLabel?.font  = diaUnselectedFont
        viernesBtn.titleLabel?.font  = diaUnselectedFont
        sabadoBtn.titleLabel?.font  = diaUnselectedFont
        domingoBtn.titleLabel?.font  = diaUnselectedFont
    }
    
    func selectMartes() {
        martesBtn.setTitleColor(diaSelectedColor, for: .normal)
        martesBtn.titleLabel?.font = diaSelectedFont
        
        lunesBtn.setTitleColor(diaUnselectedColor, for: .normal)
        miercolesBtn.setTitleColor(diaUnselectedColor, for: .normal)
        juevesBtn.setTitleColor(diaUnselectedColor, for: .normal)
        viernesBtn.setTitleColor(diaUnselectedColor, for: .normal)
        sabadoBtn.setTitleColor(diaUnselectedColor, for: .normal)
        domingoBtn.setTitleColor(diaUnselectedColor, for: .normal)
        
        lunesBtn.titleLabel?.font  = diaUnselectedFont
        miercolesBtn.titleLabel?.font  = diaUnselectedFont
        juevesBtn.titleLabel?.font  = diaUnselectedFont
        viernesBtn.titleLabel?.font  = diaUnselectedFont
        sabadoBtn.titleLabel?.font  = diaUnselectedFont
        domingoBtn.titleLabel?.font  = diaUnselectedFont
    }
    
    func selectMiercoles() {
        miercolesBtn.setTitleColor(diaSelectedColor, for: .normal)
        miercolesBtn.titleLabel?.font = diaSelectedFont
        
        lunesBtn.setTitleColor(diaUnselectedColor, for: .normal)
        martesBtn.setTitleColor(diaUnselectedColor, for: .normal)
        juevesBtn.setTitleColor(diaUnselectedColor, for: .normal)
        viernesBtn.setTitleColor(diaUnselectedColor, for: .normal)
        sabadoBtn.setTitleColor(diaUnselectedColor, for: .normal)
        domingoBtn.setTitleColor(diaUnselectedColor, for: .normal)
        
        lunesBtn.titleLabel?.font  = diaUnselectedFont
        martesBtn.titleLabel?.font  = diaUnselectedFont
        juevesBtn.titleLabel?.font  = diaUnselectedFont
        viernesBtn.titleLabel?.font  = diaUnselectedFont
        sabadoBtn.titleLabel?.font  = diaUnselectedFont
        domingoBtn.titleLabel?.font  = diaUnselectedFont
    }
    
    func selectJueves() {
        juevesBtn.setTitleColor(diaSelectedColor, for: .normal)
        juevesBtn.titleLabel?.font = diaSelectedFont
        
        lunesBtn.setTitleColor(diaUnselectedColor, for: .normal)
        martesBtn.setTitleColor(diaUnselectedColor, for: .normal)
        miercolesBtn.setTitleColor(diaUnselectedColor, for: .normal)
        viernesBtn.setTitleColor(diaUnselectedColor, for: .normal)
        sabadoBtn.setTitleColor(diaUnselectedColor, for: .normal)
        domingoBtn.setTitleColor(diaUnselectedColor, for: .normal)
        
        lunesBtn.titleLabel?.font  = diaUnselectedFont
        martesBtn.titleLabel?.font  = diaUnselectedFont
        miercolesBtn.titleLabel?.font  = diaUnselectedFont
        viernesBtn.titleLabel?.font  = diaUnselectedFont
        sabadoBtn.titleLabel?.font  = diaUnselectedFont
        domingoBtn.titleLabel?.font  = diaUnselectedFont
    }
    
    func selectViernes() {
        viernesBtn.setTitleColor(diaSelectedColor, for: .normal)
        viernesBtn.titleLabel?.font = diaSelectedFont
        
        lunesBtn.setTitleColor(diaUnselectedColor, for: .normal)
        martesBtn.setTitleColor(diaUnselectedColor, for: .normal)
        miercolesBtn.setTitleColor(diaUnselectedColor, for: .normal)
        juevesBtn.setTitleColor(diaUnselectedColor, for: .normal)
        sabadoBtn.setTitleColor(diaUnselectedColor, for: .normal)
        domingoBtn.setTitleColor(diaUnselectedColor, for: .normal)
        
        lunesBtn.titleLabel?.font  = diaUnselectedFont
        martesBtn.titleLabel?.font  = diaUnselectedFont
        miercolesBtn.titleLabel?.font  = diaUnselectedFont
        juevesBtn.titleLabel?.font  = diaUnselectedFont
        sabadoBtn.titleLabel?.font  = diaUnselectedFont
        domingoBtn.titleLabel?.font  = diaUnselectedFont
    }
    
    func selectSabado() {
        sabadoBtn.setTitleColor(diaSelectedColor, for: .normal)
        sabadoBtn.titleLabel?.font = diaSelectedFont
        
        lunesBtn.setTitleColor(diaUnselectedColor, for: .normal)
        martesBtn.setTitleColor(diaUnselectedColor, for: .normal)
        miercolesBtn.setTitleColor(diaUnselectedColor, for: .normal)
        juevesBtn.setTitleColor(diaUnselectedColor, for: .normal)
        viernesBtn.setTitleColor(diaUnselectedColor, for: .normal)
        domingoBtn.setTitleColor(diaUnselectedColor, for: .normal)
        
        lunesBtn.titleLabel?.font  = diaUnselectedFont
        martesBtn.titleLabel?.font  = diaUnselectedFont
        miercolesBtn.titleLabel?.font  = diaUnselectedFont
        juevesBtn.titleLabel?.font  = diaUnselectedFont
        viernesBtn.titleLabel?.font  = diaUnselectedFont
        domingoBtn.titleLabel?.font  = diaUnselectedFont
    }
    
    func selectDomingo() {
        domingoBtn.setTitleColor(diaSelectedColor, for: .normal)
        domingoBtn.titleLabel?.font = diaSelectedFont
        
        lunesBtn.setTitleColor(diaUnselectedColor, for: .normal)
        martesBtn.setTitleColor(diaUnselectedColor, for: .normal)
        miercolesBtn.setTitleColor(diaUnselectedColor, for: .normal)
        juevesBtn.setTitleColor(diaUnselectedColor, for: .normal)
        viernesBtn.setTitleColor(diaUnselectedColor, for: .normal)
        sabadoBtn.setTitleColor(diaUnselectedColor, for: .normal)
        
        lunesBtn.titleLabel?.font  = diaUnselectedFont
        martesBtn.titleLabel?.font  = diaUnselectedFont
        miercolesBtn.titleLabel?.font  = diaUnselectedFont
        juevesBtn.titleLabel?.font  = diaUnselectedFont
        viernesBtn.titleLabel?.font  = diaUnselectedFont
        sabadoBtn.titleLabel?.font  = diaUnselectedFont
    }
    
    
    
    
    
    
    
    
    
}
