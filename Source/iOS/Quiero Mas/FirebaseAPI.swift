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

class FirebaseAPI: NSObject {
    
    static func getInitialData() {
        FirebaseAPI.getDatosRecetas()
        FirebaseAPI.getDatosRecetasBasicas()
        FirebaseAPI.getDatosPostres()
        FirebaseAPI.getDatosPerfil()
    }
    
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
            if let perfilDic = snap.value as? [String:Any] {
                UserDefaults.standard.set(perfilDic, forKey: defPerfil)
                NotificationCenter.default.post(name: Notification.Name(rawValue: perfilLoaded), object: nil)
            } else {
                if let userDic = UserDefaults.standard.dictionary(forKey: defPerfil) {
                    if let datosDic = userDic[defPerfilDatos] as? [String:String] {
                        if let bebeDic = userDic[defPerfilBebe] as? [String:String] {
                            FirebaseAPI.storeFirebaseUser(firebaseID: firebaseID,
                                                          name: datosDic["Nombre Completo"],
                                                          birthday: datosDic["Fecha de Nacimiento"],
                                                          email: datosDic["Email"],
                                                          babyName: bebeDic["Nombre"],
                                                          babyNickName: bebeDic["Apodo"],
                                                          babyBirthday: bebeDic["Fecha de Nacimiento"])
                        }
                    }
                }
            }
        }) { (error) in
            NotificationCenter.default.post(name: Notification.Name(rawValue: connectionError), object: nil)
        }
    }
    
    static func getDatosRecetas() {
        FIRDatabase.database().reference().child(firRecetas).observeSingleEvent(of: .value, with: { (snap) in
            if let recetasDic = snap.value as? [String:Any] {
                UserDefaults.standard.set(recetasDic, forKey: defRecetas)
                NotificationCenter.default.post(name: Notification.Name(rawValue: recetasUpdated), object: nil)
            }
        }) { (error) in
            NotificationCenter.default.post(name: Notification.Name(rawValue: connectionError), object: nil)
        }
    }
    
    static func getDatosRecetasBasicas() {
        FIRDatabase.database().reference().child("Recetas Básicas").observeSingleEvent(of: .value, with: { (snap) in
            if let recetasBasicasDic = snap.value as? [String:Any] {
                UserDefaults.standard.set(recetasBasicasDic, forKey: "recetas basicas")
                NotificationCenter.default.post(name: Notification.Name(rawValue: recetasBasicasUpdated), object: nil)
            }
        }) { (error) in
            NotificationCenter.default.post(name: Notification.Name(rawValue: connectionError), object: nil)
        }
    }
    
    static func getDatosPostres() {
        FIRDatabase.database().reference().child(firPostres).observeSingleEvent(of: .value, with: { (snap) in
            if let postresDic = snap.value as? [String:Any] {
                UserDefaults.standard.set(postresDic, forKey: firPostres)
                NotificationCenter.default.post(name: Notification.Name(rawValue: postresUpdated), object: nil)
            }
        }) { (error) in
            NotificationCenter.default.post(name: Notification.Name(rawValue: connectionError), object: nil)
        }
    }
    
    static func getUser(firebaseID: String) {
        FIRDatabase.database().reference().child("Usuarios").child(firebaseID).observeSingleEvent(of: .value, with: { (snap) in
            if let userDic = snap.value as? [String:String] {
                UserDefaults.standard.set(userDic, forKey: defPerfil)
                NotificationCenter.default.post(name: Notification.Name(rawValue: perfilUpdated), object: nil)
            }
        }) { (error) in
            print(error.localizedDescription)
        }
    }
    
    static func puntuar(receta: String, puntuacion: Int) {
        let user = FIRAuth.auth()?.currentUser
        guard let firebaseID = user?.uid else {return}
        FIRDatabase.database().reference().child("Recetas").child("Por Nombre").child(receta).observeSingleEvent(of: .value, with: { (snap) in
            if let recetaDic = snap.value as? [String:Any] {
                if var puntajeDic = recetaDic["Puntaje"] as? [String:Any] {
                    var datosDic = puntajeDic["Datos"] as? [String:Int]
                    var viejoPuntaje = 0
                    if datosDic?[firebaseID] != nil {
                        viejoPuntaje = (datosDic?[firebaseID])!
                    }
                    datosDic?[firebaseID] = puntuacion
                    puntajeDic["Datos"] = datosDic
                    if let puntos = puntajeDic["Total"] as? Int {
                        puntajeDic["Total"] = puntos - viejoPuntaje + puntuacion
                    }
                    
                    FIRDatabase.database().reference().child("Recetas/Por Nombre/\(receta)/Puntaje").setValue(puntajeDic)
                } else {
                    let puntajeDic = ["Datos": [firebaseID: puntuacion], "Total": puntuacion] as [String:Any]
                    FIRDatabase.database().reference().child("Recetas/Por Nombre/\(receta)/Puntaje").setValue(puntajeDic)
                }
                NotificationCenter.default.post(name: Notification.Name(rawValue: perfilUpdated), object: nil)
            }
        }) { (error) in
            print(error.localizedDescription)
        }
    }
    
    static func getDatosEstimulacion() {
        FIRDatabase.database().reference().child(firEstimulacion).observeSingleEvent(of: .value, with: { (snap) in
            if let estimulacionDic = snap.value as? [String:Any] {
                UserDefaults.standard.set(estimulacionDic, forKey: firEstimulacion)
                NotificationCenter.default.post(name: Notification.Name(rawValue: estimulacionUpdated), object: nil)
            }
        }) { (error) in
            NotificationCenter.default.post(name: Notification.Name(rawValue: connectionError), object: nil)
        }
    }
    
    static func addToRecomendar(mails: [String]) {
        for mail in mails {
            let key = FIRDatabase.database().reference().child(firRecomendar).childByAutoId().key
            FIRDatabase.database().reference().child(firRecomendar+"/"+key).setValue(mail)
        }
    }
    
    //MARK: - Aux
    static func getRecetaByName(name: String) -> [String:Any]? {
        if let recetasDic = UserDefaults.standard.dictionary(forKey: defRecetas) {
            if let recetasDicNombre = recetasDic[firPorNombre] as? [String:[String:Any]] {
                return recetasDicNombre[name]
            }
        }
        return nil
    }
    
    static func getPostreByName(name: String) -> [String:String]? {
        if let postresDic = UserDefaults.standard.dictionary(forKey: firPostres) as? [String:[String:String]] {
            return postresDic[name]
        }
        return nil
    }
    
    static func getRecetasByNames(names: [String]) -> [[String:Any]?] {
        var recetas = [[String:Any]]()
        for name in names {
            if let r = getRecetaByName(name: name) {
                recetas.append(r)
            }
        }
        return recetas
    }
    
    static func isRecetaFavorite(name: String) -> Bool {
        if let perfilDic = UserDefaults.standard.value(forKey: defPerfil) as? [String:Any] {
            if let favoritasArr = perfilDic[firUsuarioFavoritos] as? [String] {
                for favorita in favoritasArr {
                    if favorita == name {
                        return true
                    }
                }
            }
        }
        return false
    }
    
    static func favUnfavReceta(name: String) {
        if var perfilDic = UserDefaults.standard.value(forKey: defPerfil) as? [String:Any] {
            if var favoritasArr = perfilDic[firUsuarioFavoritos] as? [String] {
                if isRecetaFavorite(name: name) {
                    var i = 0
                    while i < favoritasArr.count {
                        if favoritasArr[i] == name {
                            favoritasArr.remove(at: i)
                            i = favoritasArr.count
                        }
                        i += 1
                    }
                } else {
                    favoritasArr.append(name)
                }
                perfilDic[firUsuarioFavoritos] = favoritasArr
            } else {
                perfilDic[firUsuarioFavoritos] = [name]
            }
            UserDefaults.standard.set(perfilDic, forKey: defPerfil)
            let user = FIRAuth.auth()?.currentUser
            guard let firebaseID = user?.uid else {return}
            FIRDatabase.database().reference().child("Usuarios/\(firebaseID)/\(firUsuarioFavoritos)").setValue(perfilDic[firUsuarioFavoritos])
        }
    }
    
}
