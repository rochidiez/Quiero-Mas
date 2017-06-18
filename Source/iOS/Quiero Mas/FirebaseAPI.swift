//
//  FirebaseAPI.swift
//  Quiero Mas
//
//  Created by Fernando N. Frassia on 5/13/17.
//  Copyright © 2017 Fernando N. Frassia. All rights reserved.
//

import UIKit
import FirebaseDatabase
import CoreData
import Firebase
import FirebaseStorage

let localesStoredOrUpdatedKey = "localesStoredOrUpdatedKey"
let appMainColor = UIColor(red: 255/255, green: 147/255, blue: 96/255, alpha: 1.0)
let lactanciaUpdated = "lactanciaUpdated"
let nutricionUpdated = "nutricionUpdated"
let sobreUpdated = "sobreUpdated"
let terminosUpdated = "terminosUpdated"
let perfilLoaded = "perfilLoaded"
let perfilUpdated = "perfilUpdated"
let connectionError = "connectionError"

class FirebaseAPI: NSObject {
    
    static func storeFirebaseWithBaby(name: String,
                                  birthday: String,
                                  email: String,
                                  babyName: String,
                                  babyNickName: String,
                                  babyBirthday: String) {
        let user = FIRAuth.auth()?.currentUser
        let firebaseID = user?.uid
        FIRDatabase.database().reference().child("Usuarios").observeSingleEvent(of: .value, with: { (snap) in
            if var usuariosDic = snap.value as? [String:[String:AnyObject]] {
                let userDic = ["Bebé": ["Apodo": babyNickName, "Fecha de Nacimiento": babyBirthday, "Nombre": babyName], "Datos": ["Email": email, "Fecha de Nacimiento": birthday, "Nombre Completo": name]]
                
                usuariosDic[firebaseID!] = userDic as [String : AnyObject]?
                FIRDatabase.database().reference().child("Usuarios").setValue(usuariosDic)
            }
        }) { (error) in
            print(error.localizedDescription)
        }
    }
    
    static func storeFirebaseWithoutBaby(name: String,
                                      birthday: String,
                                      email: String,
                                      deliveryDate: String) {
        let user = FIRAuth.auth()?.currentUser
        guard let firebaseID = user?.uid else {return}
        FIRDatabase.database().reference().child("Usuarios").observeSingleEvent(of: .value, with: { (snap) in
            if var usuariosDic = snap.value as? [String:[String:AnyObject]] {
                let userDic = ["Datos": ["Email": email, "Fecha de Nacimiento": birthday, "Nombre Completo": name]]
                
                usuariosDic[firebaseID] = userDic as [String : AnyObject]?
                FIRDatabase.database().reference().child("Usuarios").setValue(usuariosDic)
            }
        }) { (error) in
            print(error.localizedDescription)
        }
        
        FIRDatabase.database().reference().child("Bebés por nacer").observeSingleEvent(of: .value, with: { (snap) in
            if var bebesDic = snap.value as? [String:String] {
                bebesDic[firebaseID] = deliveryDate
                FIRDatabase.database().reference().child("Bebés por nacer").setValue(bebesDic)
            }
        }) { (error) in
            print(error.localizedDescription)
        }
        
    }
    
    static func storeFirebaseUser(firebaseID: String,
                                      name: String?,
                                      birthday: String?,
                                      email: String?,
                                      babyName: String?,
                                      babyNickName: String?,
                                      babyBirthday: String?) {
        
        var namePosta = ""
        if name != nil {namePosta = name!}
        
        var birthdayPosta = ""
        if birthday != nil {birthdayPosta = birthday!}
        
        var emailPosta = ""
        if email != nil {emailPosta = email!}
        
        var babyNamePosta = ""
        if babyName != nil {babyNamePosta = babyName!}
        
        var babyNickNamePosta = ""
        if babyNickName != nil {babyNickNamePosta = babyNickName!}
        
        var babyBirthdayPosta = ""
        if babyBirthday != nil {babyBirthdayPosta = babyBirthday!}
        
        let nameSpaces = namePosta.replacingOccurrences(of: " ", with: "-")
        let babyNameSpaces = babyNamePosta.replacingOccurrences(of: " ", with: "-")
        let babyNicknNameSpaces = babyNickNamePosta.replacingOccurrences(of: " ", with: "-")
        var request = URLRequest(url: URL(string: "https://us-central1-quiero-mas.cloudfunctions.net/registrar?text=\(firebaseID)+\(nameSpaces)+\(birthdayPosta)+\(emailPosta)+\(babyBirthdayPosta)+\(babyNameSpaces)+\(babyNicknNameSpaces)")!)
        request.httpMethod = "POST"
        let session = URLSession.shared
        
        session.dataTask(with: request) {data, response, err in
            print("Entered the completionHandler")
            }.resume()
    }
    
    static func uploadBabyImg(img: UIImage) {
        let user = FIRAuth.auth()?.currentUser
        guard let firebaseID = user?.uid else {return}
        
        let storage = FIRStorage.storage()
        let storageRef = storage.reference()
        let bebesRef = storageRef.child("Bebes/\(firebaseID).jpeg")
        
        if let uploadData = UIImageJPEGRepresentation(img, 0) {
            bebesRef.put(uploadData, metadata: nil, completion: { (metadata, error) in
                if error != nil {
                    print(error ?? "error uploading")
                } else {
                    print(metadata ?? "error uploading")
                    if metadata?.downloadURL() != nil {
                        NotificationCenter.default.post(name: Notification.Name(rawValue: perfilUpdated), object: nil)
                    }
                }
            })
        }
    }
    
    static func getDatosLactancia() {
        FIRDatabase.database().reference().child("Lactancia").observeSingleEvent(of: .value, with: { (snap) in
            if let lactanciaDic = snap.value as? [String:String] {
                UserDefaults.standard.set(lactanciaDic, forKey: "lactancia")
                NotificationCenter.default.post(name: Notification.Name(rawValue: lactanciaUpdated), object: nil)
            }
        }) { (error) in
            print(error.localizedDescription)
        }
    }
    
    static func getDatosNutricion() {
        FIRDatabase.database().reference().child("Nutrición").observeSingleEvent(of: .value, with: { (snap) in
            if let nutricionDic = snap.value as? [String:Any] {
                UserDefaults.standard.set(nutricionDic, forKey: "nutricion")
                NotificationCenter.default.post(name: Notification.Name(rawValue: nutricionUpdated), object: nil)
            }
        }) { (error) in
            print(error.localizedDescription)
        }
    }
    
    static func getDatosSobre() {
        FIRDatabase.database().reference().child("Sobre Quiero Más!").observeSingleEvent(of: .value, with: { (snap) in
            if let sobreDic = snap.value as? [String:Any] {
                UserDefaults.standard.set(sobreDic, forKey: "sobre")
                NotificationCenter.default.post(name: Notification.Name(rawValue: sobreUpdated), object: nil)
            }
        }) { (error) in
            print(error.localizedDescription)
        }
    }
    
    static func getDatosTerminos() {
        FIRDatabase.database().reference().child("Términos y condiciones").observeSingleEvent(of: .value, with: { (snap) in
            if let terminosString = snap.value as? String {
                UserDefaults.standard.set(terminosString, forKey: "terminos")
                NotificationCenter.default.post(name: Notification.Name(rawValue: terminosUpdated), object: nil)
            }
        }) { (error) in
            print(error.localizedDescription)
        }
    }
    
    static func getDatosPerfil() {
        let user = FIRAuth.auth()?.currentUser
        guard let firebaseID = user?.uid else {return}
        FIRDatabase.database().reference().child("Usuarios").child(firebaseID).observeSingleEvent(of: .value, with: { (snap) in
            if let perfilDic = snap.value as? [String:[String:String]] {
                UserDefaults.standard.set(perfilDic, forKey: "perfil")
                NotificationCenter.default.post(name: Notification.Name(rawValue: perfilLoaded), object: nil)
            }
        }) { (error) in
            NotificationCenter.default.post(name: Notification.Name(rawValue: connectionError), object: nil)
        }
    }
    
    static func getUser(firebaseID: String) {
        FIRDatabase.database().reference().child("Usuarios").child(firebaseID).observeSingleEvent(of: .value, with: { (snap) in
            if let userDic = snap.value as? [String:String] {
                UserDefaults.standard.set(userDic, forKey: "perfil")
                NotificationCenter.default.post(name: Notification.Name(rawValue: perfilUpdated), object: nil)
            }
        }) { (error) in
            print(error.localizedDescription)
        }
    }
    
    
}
