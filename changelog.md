### Changes

* Vastly improved grist cost optimizations
	* The new ones get memory usage of grist costs down from 6 MB to ~360 kB in a somewhat heavy modded environment (previous optimizations could only get this down to ~3 MB)
* Added grist costs and combinations for Lunar Nether Neo
* Added combination recipes for:
	* batteries (`Energy Core || Redstone Torch` and `Energy Core && PlaStick`)
	* lava buckets (`Water Bucket || Netherrack`)
	* water buckets (`Bucket || Ice`)
	* potatoes (`Apple || Dirt`), so it doesn't indirectly require spider eyes
	* netherrack (`Cobblestone || Campfire`)
* The obtainability analyzer no longer complains about the new cruxite artifacts or the GristTorrent disc being unobtainable