LICENSE = "CLOSED"

SRCREV = "62e80a46e357854f1d358d91a9c972a618428552"
SRC_URI = "git://github.com/pubnub/c.git \
			file://0001-libpubnub-patch-for-poky.patch \
			"

S = "${WORKDIR}/git/"

EXTRA_OEMAKE = "'CFLAGS=${CFLAGS} -Wall -ggdb3 -O3 \
	-std=gnu99 -fPIC -fvisibility=internal \
	`pkg-config --cflags json libcurl libcrypto libevent` \
	-I${S}libpubnub -DWITHOUT_XATTR' 'BUILDDIR=${S}' \
	'LDFLAGS=-fPIC -fvisibility=internal \
	-shared -Wl,-soname,libpubnub.so.1'"

do_compile (){
	oe_runmake
}

do_install() {
	oe_runmake DESTDIR=${D} install
}