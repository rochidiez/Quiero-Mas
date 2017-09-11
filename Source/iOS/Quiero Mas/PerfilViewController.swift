//
//  PerfilViewController.swift
//  Quiero Mas
//
//  Created by Fernando N. Frassia on 5/24/17.
//  Copyright © 2017 Fernando N. Frassia. All rights reserved.
//

import UIKit
import SWRevealViewController
import Firebase
import FBSDKLoginKit
import FirebaseStorageUI

class PerfilViewController: UIViewController, UITableViewDataSource, UITableViewDelegate, UITextFieldDelegate, UIImagePickerControllerDelegate, UINavigationControllerDelegate {

    @IBOutlet weak var revealMenuButton: UIBarButtonItem!
    @IBOutlet weak var spinner: UIActivityIndicatorView!
    @IBOutlet weak var table: UITableView!
    @IBOutlet weak var editOrSaveButton: UIButton!
    @IBOutlet weak var babyImgView: UIImageView!
    @IBOutlet weak var datePickerUser: UIDatePicker!
    @IBOutlet weak var datePickerBaby: UIDatePicker!
    
    var profileDic: [String:Any]?
    var profileIsEditing = false
    var datePickedUser = false
    var datePickedBaby = false
    
    override func viewDidLoad() {
        super.viewDidLoad()
        setRevealMenuButton()
        setTable()
        setObservers()
    }
    
    func setRevealMenuButton() {
        revealMenuButton.target = self.revealViewController()
        revealMenuButton.action = #selector(SWRevealViewController.revealToggle(_:))
        self.view.addGestureRecognizer(self.revealViewController().panGestureRecognizer())
    }
    
    func setTable() {
        table.register(UINib(nibName: "PerfilStaticTableViewCell", bundle: Bundle.main), forCellReuseIdentifier: "PerfilStaticTableViewCell")
        table.register(UINib(nibName: "PerfilEditableTableViewCell", bundle: Bundle.main), forCellReuseIdentifier: "PerfilEditableTableViewCell")
    }
    
    func setObservers() {
        NotificationCenter.default.addObserver(self, selector: #selector(self.loadPerfil), name: NSNotification.Name(rawValue: perfilLoaded), object: nil)
        NotificationCenter.default.addObserver(self, selector: #selector(self.reloadPerfil), name: NSNotification.Name(rawValue: perfilUpdated), object: nil)
        NotificationCenter.default.addObserver(self, selector: #selector(self.passUpdated), name: NSNotification.Name(rawValue: perfilPassUpdated), object: nil)
        NotificationCenter.default.addObserver(self, selector: #selector(self.connectionAlert), name: NSNotification.Name(rawValue: connectionError), object: nil)
    }
    
    func loadPerfil() {
        profileDic = UserDefaults.standard.dictionary(forKey: defPerfil)
        table.reloadData()
        spinner.stopAnimating()
    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        loadPerfil()
        loadBabyImg()
    }
    
    func loadBabyImg() {
        let user = FIRAuth.auth()?.currentUser
        guard let firebaseID = user?.uid else {return}
        
        let storage = FIRStorage.storage()
        let storageRef = storage.reference()
        let bebesRef = storageRef.child("Bebes/\(firebaseID).jpeg")
        
        babyImgView.sd_setImage(with: bebesRef, placeholderImage: UIImage(named: "Circle Baby"))
        babyImgView.layer.cornerRadius = babyImgView.frame.width/2
    }
    
    func reloadPerfil() {
        loadBabyImg()
        showAlert(text: "Los cambios se guardaron correctamente")
    }
    
    func passUpdated() {
        let user = FIRAuth.auth()?.currentUser
        if let email = user?.email {
            showAlert(text: "Email de cambio de contraseña enviado exitósamente a \(email)")
        }
    }
    
    func connectionAlert() {
        spinner.stopAnimating()
        showAlert(text: "Error de conexión")
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
    
    
    //MARK: - UITableViewDataSource
    func numberOfSections(in tableView: UITableView) -> Int {
        return 1
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return 6
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        if profileIsEditing {
            let cell = tableView.dequeueReusableCell(withIdentifier: "PerfilEditableTableViewCell") as! PerfilEditableTableViewCell
            
            var titleString = ""
            switch indexPath.row {
            case 0:
                titleString = "TU NOMBRE"
            case 1:
                titleString = "TU EMAIL"
            case 2:
                titleString = "TU FECHA DE NACIMIENTO"
            case 3:
                titleString = "EL NOMBRE DE TU BEBÉ"
            case 4:
                titleString = "¿CÓMO LE DICEN?"
            case 5:
                titleString = "FECHA DE NACIMIENTO DE TU BEBÉ"
            default:
                titleString = ""
            }
            cell.titleProfile.text = titleString
            
            var descriptionString: String? = "-"
            if profileDic != nil {
                switch indexPath.row {
                case 0:
                    if let datosDic = profileDic?[defPerfilDatos] as? [String:String] {
                        descriptionString = datosDic[defPerfilDatosNombre]
                    }
                case 1:
                    if let datosDic = profileDic?[defPerfilDatos] as? [String:String] {
                        descriptionString = datosDic[defPerfilDatosEmail]
                    }
                case 2:
                    if let datosDic = profileDic?[defPerfilDatos] as? [String:String] {
                        descriptionString = datosDic[defPerfilDatosFechaDeNacimiento]
                    }
                case 3:
                    if let bebeDic = profileDic?[defPerfilBebe] as? [String:String] {
                        descriptionString = bebeDic[defPerfilBebeNombre]
                    }
                case 4:
                    if let bebeDic = profileDic?[defPerfilBebe] as? [String:String] {
                        descriptionString = bebeDic[defPerfilBebeApodo]
                    }
                case 5:
                    if let bebeDic = profileDic?[defPerfilBebe] as? [String:String] {
                        descriptionString = bebeDic[defPerfilBebeFechaDeNacimiento]
                    }
                default:
                    descriptionString = "-"
                }
            }
            cell.descriptionProfile.placeholder = descriptionString != nil ? descriptionString : "-"
            cell.descriptionProfile.delegate = self
            cell.descriptionProfile.tag = indexPath.row
            
            //load date user
            if indexPath.row == 2 {
                cell.descriptionProfile.inputView = datePickerUser
                
                let dateFormatter = DateFormatter()
                dateFormatter.dateFormat = "dd/MM/yyyy"
                let selectedDate = dateFormatter.string(from: datePickerUser.date)
                
                if datePickedUser {
                    cell.descriptionProfile.text = selectedDate
                }
            }
            
            //load date baby
            if indexPath.row == 5 {
                cell.descriptionProfile.inputView = datePickerBaby
                
                let dateFormatter = DateFormatter()
                dateFormatter.dateFormat = "dd/MM/yyyy"
                let selectedDate = dateFormatter.string(from: datePickerBaby.date)
                
                if datePickedBaby {
                    cell.descriptionProfile.text = selectedDate
                }
            }
            
            return cell
        } else {
            let cell = tableView.dequeueReusableCell(withIdentifier: "PerfilStaticTableViewCell") as! PerfilStaticTableViewCell
            
            var titleString = ""
            switch indexPath.row {
            case 0:
                titleString = "TU NOMBRE"
            case 1:
                titleString = "TU EMAIL"
            case 2:
                titleString = "TU FECHA DE NACIMIENTO"
            case 3:
                titleString = "EL NOMBRE DE TU BEBÉ"
            case 4:
                titleString = "¿CÓMO LE DICEN?"
            case 5:
                titleString = "FECHA DE NACIMIENTO DE TU BEBÉ"
            default:
                titleString = ""
            }
            cell.titleProfile.text = titleString
            
            var descriptionString: String? = "-"
            if profileDic != nil {
                switch indexPath.row {
                case 0:
                    if let datosDic = profileDic?[defPerfilDatos] as? [String:String] {
                        descriptionString = datosDic[defPerfilDatosNombre]
                    }
                case 1:
                    if let datosDic = profileDic?[defPerfilDatos] as? [String:String] {
                        descriptionString = datosDic[defPerfilDatosEmail]
                    }
                case 2:
                    if let datosDic = profileDic?[defPerfilDatos] as? [String:String] {
                        descriptionString = datosDic[defPerfilDatosFechaDeNacimiento]
                    }
                case 3:
                    if let bebeDic = profileDic?[defPerfilBebe] as? [String:String] {
                        descriptionString = bebeDic[defPerfilBebeNombre]
                    }
                case 4:
                    if let bebeDic = profileDic?[defPerfilBebe] as? [String:String] {
                        descriptionString = bebeDic[defPerfilBebeApodo]
                    }
                case 5:
                    if let bebeDic = profileDic?[defPerfilBebe] as? [String:String] {
                        descriptionString = bebeDic[defPerfilBebeFechaDeNacimiento]
                    }
                default:
                    descriptionString = "-"
                }
            }
            cell.descriptionProfile.text = descriptionString != nil ? descriptionString : "-"
            
            return cell
        }
    }
    
    
    //MARK: - UITableViewDelegate
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        return profileIsEditing ? 90 : 70
    }
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        view.endEditing(true)
    }
    
    
    //MARK: - IBAction
    @IBAction func editOrSave(_ sender: Any) {
        if profileIsEditing {
            if profileDic != nil {
                let user = FIRAuth.auth()?.currentUser
                guard let firebaseID = user?.uid else {return}
                
                //chequeo que los datos del bebé estén todos vacíos o todos llenos
                if let bebeDic = profileDic?[defPerfilBebe] as? [String:String] {
                    if (bebeDic[defPerfilBebeNombre] != nil ||
                        bebeDic[defPerfilBebeApodo] != nil ||
                        bebeDic[defPerfilBebeFechaDeNacimiento] != nil) {
                        if !(bebeDic[defPerfilBebeNombre] != nil &&
                            bebeDic[defPerfilBebeApodo] != nil &&
                            bebeDic[defPerfilBebeFechaDeNacimiento] != nil) {
                            showAlert(text: "Todos los campos del bebé deben llenarse")
                            return
                        }
                    }
                }
                
                FirebaseAPI.storeFirebaseUser(firebaseID: firebaseID,
                                              name: (profileDic?[defPerfilDatos] as? [String:String])?[defPerfilDatosNombre],
                                              birthday: (profileDic?[defPerfilDatos] as? [String:String])?[defPerfilDatosFechaDeNacimiento],
                                              email: (profileDic?[defPerfilDatos] as? [String:String])?[defPerfilDatosEmail],
                                              babyName: (profileDic?[defPerfilBebe] as? [String:String])?[defPerfilBebeNombre],
                                              babyNickName: (profileDic?[defPerfilBebe] as? [String:String])?[defPerfilBebeApodo],
                                              babyBirthday: (profileDic?[defPerfilBebe] as? [String:String])?[defPerfilBebeFechaDeNacimiento])
                UserDefaults.standard.set(profileDic, forKey: defPerfil)
                showAlert(text: "Los cambios se guardaron exitosamente")
            }
            editOrSaveButton.setTitle("Editar", for: .normal)
        } else {
            editOrSaveButton.setTitle("Guardar cambios", for: .normal)
        }
        profileIsEditing = !profileIsEditing
        table.reloadData()
        table.scrollToRow(at: IndexPath(row: 0, section: 0), at: .middle, animated: true)
    }
    
    @IBAction func logOut(_ sender:Any) {
        try! FIRAuth.auth()?.signOut()
        FBSDKLoginManager().logOut()
        UserDefaults.standard.removeObject(forKey: defPerfil)
        UserDefaults.standard.synchronize()
        let story = UIStoryboard(name: "Login", bundle: nil)
        let vc = story.instantiateInitialViewController()
        self.present(vc!, animated: true, completion: nil)
    }
    
    @IBAction func changeBabyImg(_ sender:Any) {
        let imagePickerController = UIImagePickerController()
        imagePickerController.delegate = self
        
        let actionSheet = UIAlertController(title: "", message: "", preferredStyle: .actionSheet)
        
        actionSheet.addAction(UIAlertAction(title: "Cámara", style: .default, handler: {(action: UIAlertAction) in
            imagePickerController.sourceType = .camera
            self.present(imagePickerController, animated: true, completion: nil)
        }))
        
        actionSheet.addAction(UIAlertAction(title: "Fotos", style: .default, handler: {(action: UIAlertAction) in
            imagePickerController.sourceType = .photoLibrary
            self.present(imagePickerController, animated: true, completion: nil)
        }))
        
        actionSheet.addAction(UIAlertAction(title: "Cancelar", style: .default, handler: nil))
        
        present(actionSheet, animated: true, completion: nil)
    }
    
    @IBAction func datePickerValueChanged(_ sender: UIDatePicker) {
        let dateFormatter = DateFormatter()
        dateFormatter.dateFormat = "dd/MM/yyyy"
        let selectedDatePicker = sender.tag == 0 ? datePickerUser : datePickerBaby
        let selectedDate = dateFormatter.string(from: selectedDatePicker!.date)
        
        if profileDic?[defPerfilBebe] == nil {profileDic?[defPerfilBebe] = [String:String]()}
        
        if sender.tag == 0 {
            if var datosDic = profileDic?[defPerfilDatos] as? [String:String] {
                datosDic[defPerfilDatosFechaDeNacimiento] = selectedDate
                profileDic?[defPerfilDatos] = datosDic
            }
        } else {
            if var bebeDic = profileDic?[defPerfilBebe] as? [String:String] {
                bebeDic[defPerfilBebeFechaDeNacimiento] = selectedDate
                profileDic?[defPerfilBebe] = bebeDic
            }
        }
        
        if sender.tag == 0 {
            datePickedUser = true
        } else {
            datePickedBaby = true
        }
        
        table.reloadRows(at: [IndexPath(row: sender.tag == 0 ? 2 : 5, section: 0)], with: .none)
    }
    
    @IBAction func changePass(_ sender: Any) {
        let alert = UIAlertController(title: "",
                                      message: "Cambiar contraseña?",
                                      preferredStyle: .alert)
        
        alert.addAction(UIAlertAction(title: "Cambiar", style: .default, handler: {
            alert -> Void in
                FirebaseAPI.changePassword()
        }))
        
        alert.addAction(UIAlertAction(title: "Cancelar", style: .cancel, handler: nil))
        
        present(alert, animated: true, completion: nil)
    }
    

    //MARK: - UITextfieldDelegate
    func textFieldShouldBeginEditing(_ textField: UITextField) -> Bool {
        table.scrollToRow(at: IndexPath(row: textField.tag, section: 0), at: .top, animated: true)
        return true
    }
    
    func textFieldShouldEndEditing(_ textField: UITextField) -> Bool {
        switch textField.tag {
        case 0:
            if var datosDic = profileDic?[defPerfilDatos] as? [String:String] {
                datosDic[defPerfilDatosNombre] = textField.text
                profileDic?[defPerfilDatos] = datosDic
            }
        case 1:
            if var datosDic = profileDic?[defPerfilDatos] as? [String:String] {
                datosDic[defPerfilDatosEmail] = textField.text
                profileDic?[defPerfilDatos] = datosDic
            }
        case 3:
            if textField.text == "" {
                if var bebeDic = profileDic?[defPerfilBebe] as? [String:String] {
                    bebeDic.removeValue(forKey: defPerfilBebeNombre)
                    profileDic?[defPerfilBebe] = bebeDic
                }
            } else {
                if profileDic?[defPerfilBebe] == nil {profileDic?[defPerfilBebe] = [String:String]()}
                if var bebeDic = profileDic?[defPerfilBebe] as? [String:String] {
                    bebeDic[defPerfilBebeNombre] = textField.text
                    profileDic?[defPerfilBebe] = bebeDic
                }
            }
        case 4:
            if textField.text == "" {
                if var bebeDic = profileDic?[defPerfilBebe] as? [String:String] {
                    bebeDic.removeValue(forKey: defPerfilBebeApodo)
                    profileDic?[defPerfilBebe] = bebeDic
                }
            } else {
                if profileDic?[defPerfilBebe] == nil {profileDic?[defPerfilBebe] = [String:String]()}
                if var bebeDic = profileDic?[defPerfilBebe] as? [String:String] {
                    bebeDic[defPerfilBebeApodo] = textField.text
                    profileDic?[defPerfilBebe] = bebeDic
                }
            }
        default:
            print("")
        }
        
        if let bebeDic = profileDic?[defPerfilBebe] as? [String:String] {
            if bebeDic.count == 0 {
                profileDic?.removeValue(forKey: defPerfilBebe)
            }
        }
        
        return true
    }
    
    func textFieldShouldReturn(_ textField: UITextField) -> Bool {
        view.endEditing(true)
        return true
    }
    
    
    //MARK: - Picker Controller Delegate
    func imagePickerController(_ picker: UIImagePickerController, didFinishPickingMediaWithInfo info: [String : Any]) {
        let image = info[UIImagePickerControllerOriginalImage] as! UIImage
        babyImgView.image = image
        uploadPicture()
        picker.dismiss(animated: true, completion: nil)
    }
    
    func imagePickerControllerDidCancel(_ picker: UIImagePickerController) {
        picker.dismiss(animated: true, completion: nil)
    }
    
    func uploadPicture() {
        if babyImgView.image != nil {
            FirebaseAPI.uploadBabyImg(img: babyImgView.image!)
        }
    }
    
    
    
    
    
    
    
    
    
    

}
