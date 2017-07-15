//
//  HomeViewController.swift
//  Quiero Mas
//
//  Created by Fernando N. Frassia on 3/26/17.
//  Copyright © 2017 Fernando N. Frassia. All rights reserved.
//

import UIKit
import SWRevealViewController
import Firebase
import FirebaseStorageUI

class HomeViewController: UIViewController {
    
    @IBOutlet weak var revealMenuButton: UIBarButtonItem!
    @IBOutlet weak var babyBottomConstraint: NSLayoutConstraint!
    @IBOutlet weak var nameLabel: UILabel!
    @IBOutlet weak var babyImgView: UIImageView!
    
    //Top Left
    @IBOutlet weak var tlLabel: UILabel!
    @IBOutlet weak var tlImg: UIImageView!
    @IBOutlet weak var tlImgHeightConstraint: NSLayoutConstraint!
    @IBOutlet weak var tlImgWidthConstraint: NSLayoutConstraint!
    
    //Top Right
    @IBOutlet weak var trLabel: UILabel!
    @IBOutlet weak var trImg: UIImageView!
    
    //Botom Left
    @IBOutlet weak var blLabel: UILabel!
    @IBOutlet weak var blImg: UIImageView!
    
    //Bottom Right
    @IBOutlet weak var brLabel: UILabel!
    @IBOutlet weak var brImg: UIImageView!
    
    //Circle View
    @IBOutlet weak var circleView: UIView!
    
    var picCornered = false

    override func viewDidLoad() {
        super.viewDidLoad()
        setRevealMenuButton()
        setBottomBabyConstraint()
        setListeners()
        setConstraints()
    }
    
    func setListeners() {
        NotificationCenter.default.addObserver(self, selector: #selector(self.reloadPerfil), name: NSNotification.Name(rawValue: perfilUpdated), object: nil)
        NotificationCenter.default.addObserver(self, selector: #selector(self.reloadPerfil), name: NSNotification.Name(rawValue: perfilLoaded), object: nil)
    }

    func setConstraints() {
        if DeviceType.IS_IPHONE_5 {
            tlImgWidthConstraint.constant = 35
            tlImgHeightConstraint.constant = 35
            tlLabel.font = UIFont(name: "Cera-Medium", size: 12)
            trLabel.font = UIFont(name: "Cera-Medium", size: 12)
            blLabel.font = UIFont(name: "Cera-Medium", size: 12)
            brLabel.font = UIFont(name: "Cera-Medium", size: 12)
        }
    }
    
    func setBottomBabyConstraint() {
        babyBottomConstraint.constant = self.view.frame.height/2
    }
    
    func setRevealMenuButton() {
        revealMenuButton.target = self.revealViewController()
        revealMenuButton.action = #selector(SWRevealViewController.revealToggle(_:))
        self.view.addGestureRecognizer(self.revealViewController().panGestureRecognizer())
    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        reloadPerfil()
    }
    
    func setUserName() {
        if let userDic = UserDefaults.standard.dictionary(forKey: defPerfil) {
            if let datosDic = userDic[defPerfilDatos] as? [String:String] {
                nameLabel.text = datosDic[defPerfilDatosNombre]
            }
        }
    }
    
    func setBabyImgView() {
        let user = FIRAuth.auth()?.currentUser
        guard let firebaseID = user?.uid else {return}
        
        let storage = FIRStorage.storage()
        let storageRef = storage.reference()
        let bebesRef = storageRef.child("Bebes/\(firebaseID).jpeg")
        
        babyImgView.sd_setImage(with: bebesRef, placeholderImage: UIImage(named: "Circle Baby"))
        var val = 0
        if DeviceType.IS_IPHONE_6P {
            val = 5
        } else if DeviceType.IS_IPHONE_6 {
            val = 0
        } else {
            val = -10
        }
        
        if !picCornered {
            babyImgView.layer.cornerRadius = babyImgView.frame.width/2 + CGFloat(val)
            picCornered = !picCornered
        }
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
