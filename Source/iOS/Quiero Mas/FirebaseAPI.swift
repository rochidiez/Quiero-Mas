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
        
        let uploadTask = bebesRef.putFile(url, metadata: nil) { metadata, error in
            if let error = error {
                // Uh-oh, an error occurred!
            } else {
                // Metadata contains file metadata such as size, content-type, and download URL.
                let downloadURL = metadata!.downloadURL()
            }
        }
    }
    
}

class MyClass {
    var name: String?
    var email: String?
    
}
