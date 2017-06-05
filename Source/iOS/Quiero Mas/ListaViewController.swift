//
//  ListaViewController.swift
//  Quiero Mas
//
//  Created by Fernando N. Frassia on 5/15/17.
//  Copyright © 2017 Fernando N. Frassia. All rights reserved.
//

import UIKit
import SWRevealViewController

class ListaViewController: UIViewController, UITableViewDataSource {

    @IBOutlet weak var revealMenuButton: UIBarButtonItem!
    @IBOutlet weak var orangeView: UIView!
    @IBOutlet weak var dateTF: UITextField!
    @IBOutlet weak var datePicker: UIDatePicker!
    @IBOutlet weak var table: UITableView!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        setRevealMenuButton()
        setAppMainColor()
        setDateTF()
        setTapGesture()
    }
    
    func setRevealMenuButton() {
        revealMenuButton.target = self.revealViewController()
        revealMenuButton.action = #selector(SWRevealViewController.revealToggle(_:))
        self.view.addGestureRecognizer(self.revealViewController().panGestureRecognizer())
    }
    
    func setAppMainColor() {
        view.backgroundColor = appMainColor
        orangeView.backgroundColor = appMainColor
        table.backgroundColor = appMainColor
    }
    
    func setDateTF() {
        dateTF.inputView = datePicker
    }
    
    func setTapGesture() {
        let tapGesture = UITapGestureRecognizer(target: self, action: #selector(self.tapGestureHandler))
        self.view.addGestureRecognizer(tapGesture)
    }
    
    func tapGestureHandler() {
        self.view.endEditing(true)
    }
    
    
    //MARK: - UITableView Data Source
    func numberOfSections(in tableView: UITableView) -> Int {
        return 1
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return 5
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "ListaTableViewCell", for: indexPath) as! ListaTableViewCell
        
        var text = ""
        switch indexPath.row {
        case 0:
            text = "1 manzana"
        case 1:
            text = "2 bananas"
        case 2:
            text = "100gr de avena"
        case 3:
            text = "2 huevos"
        case 4:
            text = "200gr de manteca"
        default:
            text = ""
        }
        
        cell.title.text = text
        
        return cell
    }
    
    
    //MARK: - IBAction
    @IBAction func datePickerValueChanged(_ sender: Any) {
        let dateFormatter = DateFormatter()
        dateFormatter.dateFormat = "dd/MM/yyyy"
        let selectedDate = dateFormatter.string(from: datePicker.date)
        dateTF.text = selectedDate
    }

}
