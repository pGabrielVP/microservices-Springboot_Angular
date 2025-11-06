/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/SQLTemplate.sql to edit this template
 */
/**
 * Author:  paulo
 * Created: 2 de nov. de 2025
 */

CREATE TABLE `t_inventory`
(
    `id`       bigint(20) NOT NULL AUTO_INCREMENT,
    `sku_code`  varchar(255) DEFAULT NULL,
    `quantity` int(11)      DEFAULT NULL,
    PRIMARY KEY (`id`)
);
