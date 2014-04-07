# JPA Minefield

## Vagrant setup

There is a support for Vagrant in this project. Setup is standard Vagrantfile in root directory of project.

Reason for use Vagrant is to provide whole environment that is needed to run example projects. 

For now Vagrant will "up" default Ubuntu 12.04 LTS 32 bit machine (hashicorp/precise32). In future further configuration will be provider (like installed and configured databases)

### Environment setup instruction

You need install Vagrant on your local machine. [Vagrant documentation](http://docs.vagrantup.com/v2/installation/index.html "Vagrant documentation") will help you.

Next, you need to go to root directory of project and run:

    vagrant up
	
And that's all - Vagrant will setup whole machine for you. Then if you want, you can go to log in to your machine

    vargant ssh
