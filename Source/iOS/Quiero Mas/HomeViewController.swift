//
//  HomeViewController.swift
//  Quiero Mas
//
//  Created by Fernando N. Frassia on 3/26/17.
//  Copyright © 2017 Fernando N. Frassia. All rights reserved.
//

import UIKit
import SWRevealViewController

class HomeViewController: UIViewController {
    
    @IBOutlet weak var revealMenuButton: UIBarButtonItem!
    @IBOutlet weak var babyBottomConstraint: NSLayoutConstraint!
    @IBOutlet weak var nameLabel: UILabel!
    @IBOutlet weak var babyImgView: UIImageView!
    
    //Circle View
    @IBOutlet weak var circleView: UIView!

    override func viewDidLoad() {
        super.viewDidLoad()
        setRevealMenuButton()
        setBottomBabyConstraint()
        setCircleView()
        NotificationCenter.default.addObserver(self, selector: #selector(self.reloadPerfil), name: NSNotification.Name(rawValue: perfilUpdated), object: nil)
    }

    func setBottomBabyConstraint() {
        babyBottomConstraint.constant = self.view.frame.height/2
    }
    
    func setRevealMenuButton() {
        revealMenuButton.target = self.revealViewController()
        revealMenuButton.action = #selector(SWRevealViewController.revealToggle(_:))
        self.view.addGestureRecognizer(self.revealViewController().panGestureRecognizer())
    }
    
    func setCircleView() {
        circleView.layer.cornerRadius = circleView.frame.width/2
    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        setUserName()
        setBabyImgView()
    }
    
    func setUserName() {
        if let userDic = UserDefaults.standard.dictionary(forKey: "usuario") {
            nameLabel.text = userDic["nombre"] as? String
        }
    }
    
    func setBabyImgView() {
        if let userDic = UserDefaults.standard.dictionary(forKey: "usuario") {
            if let url = userDic["foto"] as? String {
                babyImgView.sd_setImage(with: URL(string: url), placeholderImage: UIImage(named: "Circle Baby"))
            }
        }
        babyImgView.layer.cornerRadius = babyImgView.frame.width/2
    }
    
    func reloadPerfil() {
        setUserName()
        setBabyImgView()
    }
    
    //MARK: - IBAction
    @IBAction func planNutricionAction(_ sender: Any) {
        let story = UIStoryboard(name: "Main", bundle: nil)
        let planVC = story.instantiateViewController(withIdentifier: "PlanNav")
        self.revealViewController().pushFrontViewController(planVC, animated: true)
    }
    
    @IBAction func estimulacionAction(_ sender: Any) {
        let story = UIStoryboard(name: "Main", bundle: nil)
        let planVC = story.instantiateViewController(withIdentifier: "EstimulacionNav")
        self.revealViewController().pushFrontViewController(planVC, animated: true)
    }
    
    @IBAction func abcNutricionAction(_ sender: Any) {
        let story = UIStoryboard(name: "Main", bundle: nil)
        let planVC = story.instantiateViewController(withIdentifier: "ABCNutricionNav")
        self.revealViewController().pushFrontViewController(planVC, animated: true)
    }
    
    @IBAction func lactanciaAction(_ sender: Any) {
        let story = UIStoryboard(name: "Main", bundle: nil)
        let planVC = story.instantiateViewController(withIdentifier: "LactanciaNav")
        self.revealViewController().pushFrontViewController(planVC, animated: true)
    }
    
    
    
    
    
    
    

}
