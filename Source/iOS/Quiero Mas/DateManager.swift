//
//  DateManager.swift
//  Quiero Mas
//
//  Created by Fernando N. Frassia on 6/30/17.
//  Copyright © 2017 Fernando N. Frassia. All rights reserved.
//

import Foundation

class DateManager: NSObject {
    
    static func getBabyAgeString(birthday: String, currentDate: Date) -> String {
        return parseBabyAgeTuple(q: getBabyAgeTuple(birthday: birthday, currentDate: currentDate))
    }
    
    static func getBabyAgeTuple(birthday: String, currentDate: Date) -> QuieroDate {
        let dateFormatter = DateFormatter()
        dateFormatter.dateFormat = "dd/MM/yyyy"
        let birthdayDate = dateFormatter.date(from: birthday)!
        
        let calendar = Calendar.current
        let component: Set<Calendar.Component> = [.day, .month, .year]
        let timeDifference = calendar.dateComponents(component, from: birthdayDate, to: currentDate)

        let months = timeDifference.month! + timeDifference.year! * 12
        let weeks = timeDifference.day!/7
        let days = (timeDifference.day! % 7) + 1
        
        return QuieroDate(mes: months, semana: weeks, dia: days)
    }
    
    static func parseBabyAgeTuple(q: QuieroDate) -> String {
        let semanas = q.semana == 1 ? "semana" : "semanas"
        return "\(q.mes) meses, \(q.semana) \(semanas)"
    }
    
    static func getCurrentDay() -> Int {
        let date = Date()
        let calendar = Calendar.current
        let components = calendar.dateComponents([.weekday], from: date)
        return components.weekday!
    }
    
    static func getBabyDayInPlan() throws -> Int {
        if let userDic = UserDefaults.standard.dictionary(forKey: defPerfil) {
            if let bebeDic = userDic[defPerfilBebe] as? [String:String] {
                if let dateString = bebeDic[defPerfilBebeFechaDeNacimiento] {
                    let dateFormatter = DateFormatter()
                    dateFormatter.dateFormat = "dd/MM/yyyy"
                    let birthdayDate = dateFormatter.date(from: dateString)!
                    
                    let currentDate = Date()
                    let calendar = Calendar.current
                    let component: Set<Calendar.Component> = [.day]
                    let timeDifference = calendar.dateComponents(component, from: birthdayDate, to: currentDate)
                    
                    let days = timeDifference.day! - 181
                    
                    guard days >= 0 && days <= 185 else {
                        throw DateManagerError.dayOutOfBounds
                    }
                    
                    return days
                }
            }
        }
        return 0
    }
    
    static func getDateFromIndexInPlan(indexToTransform: Int, currentIndex: Int) -> String {
        let daysToAdd = indexToTransform - currentIndex
        let futureDate = getDateByAddingDays(date: Date(), daysToAdd: daysToAdd)
        
        let dateFormatter = DateFormatter()
        dateFormatter.dateFormat = "dd/MM"
        return dateFormatter.string(from: futureDate)
    }
    
    static func getDateByAddingDays(date: Date, daysToAdd: Int) -> Date {
        var dateComponent = DateComponents()
        dateComponent.day = daysToAdd
        return Calendar.current.date(byAdding: dateComponent, to: date)!
    }
    
    static func getDateStringByAddingDays(date: Date, daysToAdd: Int) -> String {
        let futureDate = getDateByAddingDays(date: date, daysToAdd: daysToAdd)
        
        let dateFormatter = DateFormatter()
        dateFormatter.dateFormat = "dd/MM"
        return dateFormatter.string(from: futureDate)
    }
    
}

struct QuieroDate {
    var mes: Int
    var semana: Int
    var dia: Int
}

enum DateManagerError: Error {
    case dayOutOfBounds
}



