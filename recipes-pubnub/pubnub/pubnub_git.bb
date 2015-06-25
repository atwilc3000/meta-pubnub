LICENSE = "CLOSED"

DEPENDS = "libpubnub"

SRCREV = "640ccbfe3fd50dae3bf4a0e6f68abfd2d6149388"
SRC_URI = "git://github.com/pubnub/sama5d3.git \
		file://0001-pubnub_samaple_patch_for_poky.patch"

S = "${WORKDIR}/git/"

EXTRA_OEMAKE = "'CC=${CC} \
	`pkg-config --libs libpubnub json openssl`' \
	'CFLAGS=${CFLAGS} -std=gnu99 \
	`pkg-config --cflags libpubnub json openssl`'"

do_compile (){
	oe_runmake
}

do_install () {
    oe_runmake DESTDIR=${D} install
}