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
    
    //Circle View
    @IBOutlet weak var circleView: UIView!

    override func viewDidLoad() {
        super.viewDidLoad()

        setRevealMenuButton()
        setBottomBabyConstraint()
        setCircleView()
    }

    func setBottomBabyConstraint() {
        babyBottomConstraint.constant = self.view.frame.height/2
    }
    
    func setRevealMenuButton() {
        revealMenuButton.target = self.revealViewController()
        revealMenuButton.action = Selector(("revealToggle:"))
        self.view.addGestureRecognizer(self.revealViewController().panGestureRecognizer())
    }
    
    func setCircleView() {
        circleView.layer.cornerRadius = circleView.frame.width/2
    }
    
    
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
    
    //MARK: - IBAction
    
    
    
    
    

}
