
DROP DATABASE IF EXISTS `model1`;

CREATE DATABASE `model1`;

USE `model1`;

/****** Object:  Table userinfo (用户表)  ******/
CREATE TABLE IF NOT EXISTS `userinfo`(
  `id`                               int(11)                                                  NOT NULL auto_increment, /* 编号 */
  `username`                         varchar(100)                                             NOT NULL collate utf8_bin, /* 用户编号 */
  `loginname`                        varchar(100)                                             NOT NULL collate utf8_bin, /*登陆名称 */
  `password`                         varchar(50)                                              NOT NULL collate utf8_bin, /*密码 */
  `notes`                            varchar(50)                                              NOT NULL collate utf8_bin, /*登陆名称 */
 PRIMARY KEY  (`id`) 
)ENGINE=MyISAM  DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/****** Object:  Table userroleinfo (用户--角色 对照表)  ******/
CREATE TABLE IF NOT EXISTS `userroleinfo`(
  `id`                               int(11)                                                         NOT NULL auto_increment, /* 编号 */
  `userid`                           int(11)                                                         NOT NULL collate utf8_bin, /* 用户编号 */
  `roleid`                           varchar(2000)                                                   NOT NULL collate utf8_bin, /* 角色编号 */
 PRIMARY KEY  (`id`)
)ENGINE=MyISAM  DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/****** Object:  Table stmkinfo (系统权限表) ******/
CREATE TABLE IF NOT EXISTS `stmkinfo`(
  `id`                                 int(11)                                                           NOT NULL auto_increment, /* 编号 */
  `pename`                             varchar(50)                                                           NULL collate utf8_bin, /* 名称 */
  `kname`                              varchar(200)                                          UNIQUE      NOT NULL collate utf8_bin, /* 语言键: */
  `isfolder`                           varchar(1)                                           DEFAULT 'N'  NOT NULL collate utf8_bin, /* 是/否 文件夹 */
  `parentid`                           int(11)                                                           NOT NULL collate utf8_bin, /* 父ID,如果是根目录： -1  */
  `perlink`                            varchar(256)                                                          NULL collate utf8_bin, /* URL  */
  `ptype`                              varchar(4)                                            DEFAULT '0' NOT NULL collate utf8_bin, /* 分类： 1--权限,2--功能菜单 */
  `content`                            varchar(1000)                                                         NULL collate utf8_bin, /* 注释 */
  `ncom`	                           int				                                                     NULL collate utf8_bin, /*序号*/
 PRIMARY KEY  (`id`)
)ENGINE=MyISAM  DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/****** Object:  Table modleinfo (角色----模块表)  ******/
CREATE TABLE IF NOT EXISTS `modleinfo`(
  `id`                                       int(11)                                                         NOT  NULL auto_increment, /* 编号 */
  `modelname`                                varchar(50)                                                          NULL collate utf8_bin, /* 角色名称  角色分类 1：系统管理员  2:普通管理员  */
  `kname`                                    varchar(200)                                           UNIQUE   NOT  NULL collate utf8_bin, /* 语言键: */	
  `content`                                  varchar(1000)                                                        NULL collate utf8_bin, /* 注释 */
  `rtype`                                    int(11)                                                 DEFAULT 0    NULL collate utf8_bin, /* 角色类型 0: 系统管理员（管理员加入的），1：普通管理员  （用户加入的）*/
  `permissionid`                             varchar(2000)                                                        NULL collate utf8_bin, /* 角色权限 ,用逗号间隔 */
  PRIMARY KEY  (`id`)
)ENGINE=MyISAM  DEFAULT CHARSET=utf8 COLLATE=utf8_bin;



INSERT INTO userinfo(password,username,loginname,notes)VALUES ('pDdqVlwXj5ero','admin' ,'admin','');/**000000***/   -- admin  :  1111

INSERT INTO stmkinfo(pename ,kname,isfolder ,parentid ,perlink ,ptype ,content,ncom) VALUES ('权限管理' ,'permission' ,'Y' ,'-1' ,'jj' ,'2' ,'kk' ,'1');
INSERT INTO stmkinfo(pename ,kname,isfolder ,parentid ,perlink ,ptype ,content,ncom) VALUES ('角色管理' ,'role' ,'N' ,'1' ,'RolePermission!list.action' ,'2' ,'kk' ,'2');
INSERT INTO stmkinfo(pename ,kname,isfolder ,parentid ,perlink,ptype ,content,ncom) VALUES ('用户管理' ,'usermanger' ,'N' ,'1' ,'Users!list.action' ,'2' ,'kk' ,'3');
INSERT INTO stmkinfo(pename ,kname,isfolder ,parentid ,perlink,ptype ,content,ncom) VALUES ('模块管理' ,'1346916608487' ,'N' ,'1' ,'Permission!list.action' ,'2' ,'kk' ,'4');
INSERT INTO stmkinfo (pename ,kname,isfolder ,parentid ,perlink ,ptype ,content,ncom)VALUES ( '角色新增', '1358752144790', 'N', '2', 'RolePermission!add.action', '1', '', '1');
INSERT INTO stmkinfo (pename ,kname,isfolder ,parentid ,perlink ,ptype ,content,ncom)VALUES ('角色编辑', '1358752176449', 'N', '2', 'RolePermission!edit.action', '1', '', '2');
INSERT INTO stmkinfo (pename ,kname,isfolder ,parentid ,perlink ,ptype ,content,ncom)VALUES ('角色删除', '1358752232571', 'N', '2', 'RolePermission!delete.action', '1', '', '3');
INSERT INTO stmkinfo (pename ,kname,isfolder ,parentid ,perlink ,ptype ,content,ncom)VALUES ( '用户新增', '1358752284051', 'N', '3', 'Users!add.action', '1', '', '1');
INSERT INTO stmkinfo (pename ,kname,isfolder ,parentid ,perlink ,ptype ,content,ncom)VALUES ('用户修改', '1358752341425', 'N', '3', 'Users!edit.action', '1', '', '2');
INSERT INTO stmkinfo (pename ,kname,isfolder ,parentid ,perlink ,ptype ,content,ncom)VALUES ( '用户修改密码', '1358752386777', 'N', '3', 'UserPassword!enter.action', '1', '', '3');
INSERT INTO stmkinfo (pename ,kname,isfolder ,parentid ,perlink ,ptype ,content,ncom)VALUES ( '用户分配角色', '1358752416499', 'N', '3', 'Users!roleset.action', '1', '', '4');
INSERT INTO stmkinfo (pename ,kname,isfolder ,parentid ,perlink ,ptype ,content,ncom)VALUES ( '用户删除', '1358752476011', 'N', '3', 'Users!delete.action', '1', '', '5');
INSERT INTO stmkinfo (pename ,kname,isfolder ,parentid ,perlink ,ptype ,content,ncom)VALUES ( '用户列表', '1358317530601', 'N', '1', 'Users!userlist.action', '2', '', '4');

INSERT INTO modleinfo(modelname ,kname,content ,rtype,permissionid) VALUES ('超级管理员' ,'admin' ,'ad' ,'0' ,'1,2,3,4,5,6,7,8,9,10,11,12');

INSERT INTO userroleinfo(userid ,roleid)VALUES ('1' ,'1');

