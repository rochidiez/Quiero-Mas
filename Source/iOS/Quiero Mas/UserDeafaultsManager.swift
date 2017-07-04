//
//  UserDeafaultsManager.swift
//  Quiero Mas
//
//  Created by Fernando N. Frassia on 6/24/17.
//  Copyright © 2017 Fernando N. Frassia. All rights reserved.
//

import Foundation
import FirebaseAuth

class UserDefaultsManager: NSObject {

    static func setearPuntajeEnUserDefaults(recetaNombre: String?, puntaje: Int) {
        let user = FIRAuth.auth()?.currentUser
        guard let firebaseID = user?.uid else {return}
        if recetaNombre != nil {
            if var recetasDic = UserDefaults.standard.dictionary(forKey: defRecetas) as? [String:[String:Any]] {
                if var porNombreDic = recetasDic[firPorNombre] {
                    if var recetaDic = porNombreDic[recetaNombre!] as? [String:Any] {
                        if var puntajeDic = recetaDic[firRecetaPuntaje] as? [String:Any] {
                            if var datosDic = puntajeDic[firRecetaPuntajeDatos] as? [String:Int] {
                                var viejoPuntaje = 0
                                if datosDic[firebaseID] != nil {
                                    viejoPuntaje = datosDic[firebaseID]!
                                }
                                
                                datosDic[firebaseID] = puntaje
                                if let puntos = puntajeDic[firRecetaPuntajeTotal] as? Int {
                                    puntajeDic[firRecetaPuntajeTotal] = puntos - viejoPuntaje + puntaje
                                }
                                puntajeDic[firRecetaPuntajeDatos] = datosDic
                            }
                            recetaDic[firRecetaPuntaje] = puntajeDic
                        } else {
                            recetaDic[firRecetaPuntaje] = [firRecetaPuntajeDatos: [firebaseID:puntaje], firRecetaPuntajeTotal: puntaje]
                        }
                        porNombreDic[recetaNombre!] = recetaDic
                    }
                    recetasDic[firPorNombre] = porNombreDic
                }
                UserDefaults.standard.set(recetasDic, forKey: defRecetas)
            }
        }
    }
    
    
}
