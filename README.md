# meta-pubnub
<a href="http://www.atmel.com"><img src="http://www.atmel.com/Images/atmel.png" align="left" hspace="10" vspace="6"></a>

This repository provides PubNub Example for SAMA5D3 Xplained on Poky.


This client example is a fairly trivial C PubNub app that connects your board to the world via a PubNub channel.
  * Messages are sent to PubNub dev console in cloud whenever the PB_USER button is pressed.
  * According to received messages by SAMA5D3 Xplained, the blue and red LEDs can be switched.

##Sources
#### meta-pubnub
* URI: git://github.com/atwilc3000/meta-pubnub.git
* URI: https://github.com/atwilc3000/meta-pubnub.git

##Dependencies
#### meta-atmel
* URI: git://github.com/linux4sam/meta-atmel.git
* URI: https://github.com/linux4sam/meta-atmel.git
* Branch: dizzy

#### meta-openembedded
* URI: git://git.openembedded.org/meta-openembedded
* URI: http://cgit.openembedded.org/meta-openembedded/
* Branch: dizzy

#### meta-qt5
* URI: git://github.com/meta-qt5/meta-qt5.git
* URI: https://github.com/meta-qt5/meta-qt5
* Branch: master

#### meta-wilc
* URI: git://github.com/atwilc3000/meta-wilc.git
* URI: https://github.com/atwilc3000/meta-wilc.git

##Build procedure
#####1. Clone yocto/poky git repository
~~~
git clone git://git.yoctoproject.org/poky
~~~
#####2. Checkout dizzy branch
~~~
cd poky
git checkout origin/dizzy -b my_branch
~~~
#####3. Clone meta-openembedded git repository
~~~
git clone git://git.openembedded.org/meta-openembedded
~~~
#####4. Checkout proper meta-openembedded branch
~~~
cd meta-openembedded
git checkout origin/dizzy -b my_branch
# come back to the "poky" directory for next steps
cd ..
~~~
#####5. Clone meta-qt5 git repository
~~~
git clone git://github.com/meta-qt5/meta-qt5.git
~~~
#####6. Checkout proper meta-qt5 branch
~~~
cd meta-qt5
git checkout origin/master -b my_branch
# come back to the "poky" directory for next steps
cd ..
~~~
#####7. Clone meta-atmel layer
~~~
git clone git://github.com/linux4sam/meta-atmel.git meta-atmel
~~~
#####8. Checkout proper meta-atmel branch
~~~
cd meta-atmel
git checkout origin/dizzy -b my_branch
# come back to the "poky" directory for next steps
cd ..
~~~
#####9. Clone meta-wilc layer
~~~
git clone https://github.com/atwilc3000/meta-wilc.git
# come back to the "poky" directory for next steps
cd ..
~~~
#####10. Clone meta-pubnub layer
~~~
git clone https://github.com/atwilc3000/meta-pubnub.git
# come back to the "poky" directory for next steps
cd ..
~~~
#####11. Initialize build directory
~~~
source oe-init-build-env build-atmel
~~~
#####12. Add meta-wilc layer patch to bblayer configuration file
~~~
vim conf/bblayers.conf
~~~
~~~
# LAYER_CONF_VERSION is increased each time build/conf/bblayers.conf
# changes incompatibly
LCONF_VERSION = "6"

BBPATH = "${TOPDIR}"
BBFILES ?= ""

BSPDIR := "${@os.path.abspath(os.path.dirname(d.getVar('FILE', True)) + '/../..')}"

BBLAYERS ?= " \
  ${BSPDIR}/meta-atmel \
  ${BSPDIR}/meta-qt5 \
  ${BSPDIR}/meta \
  ${BSPDIR}/meta-yocto \
  ${BSPDIR}/meta-yocto-bsp \
  ${BSPDIR}/meta-openembedded/meta-oe \
  ${BSPDIR}/meta-openembedded/meta-networking \
  ${BSPDIR}/meta-openembedded/meta-python \
  ${BSPDIR}/meta-openembedded/meta-ruby \
  ${BSPDIR}/meta-wilc \
  ${BSPDIR}/meta-pubnub \
  "
~~~
#####13. Edit local.conf to specify the machine, location of source archived, package type (rpm, deb or ipk)
~~~
vim conf/local.conf
~~~
~~~
[...]
MACHINE ??= "sama5d3-xplained"
[...]
DL_DIR ?= "your_download_directory_path"
[...]
PACKAGE_CLASSES ?= "package_ipk"
~~~
Here are the machines that are supported:
* MACHINE ??= "at91sam9rlek"
* MACHINE ??= "at91sam9m10g45ek"
* MACHINE ??= "at91sam9x5ek"
* MACHINE ??= "sama5d3xek"
* MACHINE ??= "sama5d3-xplained"
* MACHINE ??= "sama5d4ek"
* MACHINE ??= "sama5d4-xplained"

To get better performance, use the poky-atmel distribution by also adding that
line:
~~~
DISTRO = "poky-atmel"
~~~
Input correct package name
~~~  
#Atmel
IMAGE_INSTALL_append += " \
  linux-firmware-atmel-wilc3000 \
  wilc3000 
  libpubnub
  pubnub
~~~
#####14. We found that additional local.conf changes are needed for our QT demo image. 
You can add these two lines at the end of the file:
~~~
vim conf/local.conf
~~~
~~~
[...]
LICENSE_FLAGS_WHITELIST += "commercial"
SYSVINIT_ENABLED_GETTYS = ""
~~~
#####15. Build Json-c
~~~
bitbake json-c
~~~
#####16. Build Atmel demo images
~~~
bitbake atmel-qt5-demo-image
~~~
