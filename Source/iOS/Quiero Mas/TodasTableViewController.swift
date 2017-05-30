//
//  TodasViewController.swift
//  Quiero Mas
//
//  Created by Fernando N. Frassia on 5/15/17.
//  Copyright © 2017 Fernando N. Frassia. All rights reserved.
//

import UIKit
import SWRevealViewController

class TodasTableViewController: UITableViewController {

    @IBOutlet weak var revealMenuButton: UIBarButtonItem!
    @IBOutlet weak var orangeView: UIView!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        setRevealMenuButton()
        setAppMainColor()
    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        self.navigationController?.navigationBar.backgroundColor = appMainColor
    }
    
    override func viewWillDisappear(_ animated: Bool) {
        super.viewWillDisappear(animated)
        self.navigationController?.navigationBar.backgroundColor = .clear
    }
    
    func setRevealMenuButton() {
        revealMenuButton.target = self.revealViewController()
        revealMenuButton.action = Selector(("revealToggle:"))
        self.view.addGestureRecognizer(self.revealViewController().panGestureRecognizer())
    }
    
    func setAppMainColor() {
        tableView.backgroundColor = appMainColor
        orangeView.backgroundColor = appMainColor
    }
    
    
    //MARK: - UITableView Data Source
    override func numberOfSections(in tableView: UITableView) -> Int {
        return 2
    }
    
    override func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return 1
    }
    
    override func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "TodasTableViewCell", for: indexPath) as! TodasTableViewCell
        
        return cell
    }
    
    
    //MARK: - UITableView Delegate
    override func tableView(_ tableView: UITableView, heightForHeaderInSection section: Int) -> CGFloat {
        return 5
    }
    
    override func tableView(_ tableView: UITableView, viewForHeaderInSection section: Int) -> UIView? {
        let hView = UIView()
        hView.backgroundColor = .white
        return hView
    }
    
    
    
}
