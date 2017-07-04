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

        let months = timeDifference.month!
        let weeks = timeDifference.day! % 7 == 0 ? timeDifference.day!/7 : timeDifference.day!/7 + 1
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
    
}

struct QuieroDate {
    var mes: Int
    var semana: Int
    var dia: Int
}




