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
                                      name: String,
                                      birthday: String,
                                      email: String,
                                      babyName: String,
                                      babyNickName: String,
                                      babyBirthday: String) {
        let nameSpaces = name.replacingOccurrences(of: " ", with: "-")
        let babyNameSpaces = babyName.replacingOccurrences(of: " ", with: "-")
        let babyNicknNameSpaces = babyNickName.replacingOccurrences(of: " ", with: "-")
        var request = URLRequest(url: URL(string: "https://us-central1-quiero-mas.cloudfunctions.net/registrar?text=\(firebaseID)+\(nameSpaces)+\(birthday)+\(email)+\(babyBirthday)+\(babyNameSpaces)+\(babyNicknNameSpaces)")!)
        request.httpMethod = "POST"
        let session = URLSession.shared
        
        session.dataTask(with: request) {data, response, err in
            print("Entered the completionHandler")
            }.resume()
    }
    
    static func uploadBabyImg(url: URL, firebaseID: String) {
        let storage = FIRStorage.storage()
        let storageRef = storage.reference()

        let bebesRef = storageRef.child("bebes/\(firebaseID).jpg")
        
        _ = bebesRef.putFile(url, metadata: nil) { metadata, error in
            if let error = error {
                print(error)
            }
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
    
}

class MyClass {
    var name: String?
    var email: String?
    
}
