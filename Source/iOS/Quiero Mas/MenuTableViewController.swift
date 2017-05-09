//
//  MenuTableViewController.swift
//  Quiero Mas
//
//  Created by Fernando N. Frassia on 3/26/17.
//  Copyright © 2017 Fernando N. Frassia. All rights reserved.
//

import UIKit

class MenuTableViewController: UITableViewController {
    
    @IBOutlet weak var revealMenuButton: UIBarButtonItem!

    override func viewDidLoad() {
        super.viewDidLoad()

    }

    func setRevealMenuButton() {
        revealMenuButton.action = Selector(("revealToggle:"))
    }
    

    // MARK: - Table view data source
    override func numberOfSections(in tableView: UITableView) -> Int {
        return 1
    }

    override func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return 5
    }

    
    override func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "MenuCell", for: indexPath) as! MenuTableViewCell

        switch indexPath.row {
        case 0:
            cell.name.text = "Inicio"
        case 1:
            cell.name.text = "Plan de nutrición"
        case 2:
            cell.name.text = "Estimulación y educación"
        case 3:
            cell.name.text = "Nutrición"
        case 4:
            cell.name.text = "Lactancia"
        default:
            cell.name.text = ""
        }

        return cell
    }
    
    override func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        switch indexPath.row {
        case 0:
            let story = UIStoryboard(name: "Main", bundle: nil)
            let planVC = story.instantiateViewController(withIdentifier: "HomeNav")
            self.revealViewController().pushFrontViewController(planVC, animated: true)
        case 1:
            let story = UIStoryboard(name: "Main", bundle: nil)
            let planVC = story.instantiateViewController(withIdentifier: "PlanNav")
            self.revealViewController().pushFrontViewController(planVC, animated: true)
        case 2:
            let story = UIStoryboard(name: "Main", bundle: nil)
            let planVC = story.instantiateViewController(withIdentifier: "EstimulacionNav")
            self.revealViewController().pushFrontViewController(planVC, animated: true)
        case 3:
            let story = UIStoryboard(name: "Main", bundle: nil)
            let planVC = story.instantiateViewController(withIdentifier: "ABCNutricionNav")
            self.revealViewController().pushFrontViewController(planVC, animated: true)
        case 4:
            let story = UIStoryboard(name: "Main", bundle: nil)
            let planVC = story.instantiateViewController(withIdentifier: "LactanciaNav")
            self.revealViewController().pushFrontViewController(planVC, animated: true)
        default:
            print("")
        }
    }
 

    /*
    // Override to support conditional editing of the table view.
    override func tableView(_ tableView: UITableView, canEditRowAt indexPath: IndexPath) -> Bool {
        // Return false if you do not want the specified item to be editable.
        return true
    }
    */

    /*
    // Override to support editing the table view.
    override func tableView(_ tableView: UITableView, commit editingStyle: UITableViewCellEditingStyle, forRowAt indexPath: IndexPath) {
        if editingStyle == .delete {
            // Delete the row from the data source
            tableView.deleteRows(at: [indexPath], with: .fade)
        } else if editingStyle == .insert {
            // Create a new instance of the appropriate class, insert it into the array, and add a new row to the table view
        }    
    }
    */

    /*
    // Override to support rearranging the table view.
    override func tableView(_ tableView: UITableView, moveRowAt fromIndexPath: IndexPath, to: IndexPath) {

    }
    */

    /*
    // Override to support conditional rearranging of the table view.
    override func tableView(_ tableView: UITableView, canMoveRowAt indexPath: IndexPath) -> Bool {
        // Return false if you do not want the item to be re-orderable.
        return true
    }
    */

    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using segue.destinationViewController.
        // Pass the selected object to the new view controller.
    }
    */
    
    //MARK: - IBAction
    

}
