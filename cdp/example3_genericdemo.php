<?php
/**
 * example3_genericdemo.php
 * 22-Nov-2009
 *
 * PHP Version 5
 *
 * @category Services
 * @package  Services_OpenStreetMap
 * @author   Ken Guest <kguest@php.net>
 * @license  BSD http://www.opensource.org/licenses/bsd-license.php
 * @version  CVS: <cvs_id>
 * @link     osmx.php
*/

$version = '@package_version@';
if (strstr($version, 'package_version')) {
    set_include_path(dirname(dirname(__FILE__)) . ':' . get_include_path());
}

require_once 'Services/OpenStreetMap.php';

$osm = new Services_OpenStreetMap();

// var_dump($osm->getCoordsOfPlace("Nenagh, Ireland"));

$config = array(
    'user' => 'vprajan@gmail.com',
    'password' => 't123cdp4',
	'server' => 'http://api06.dev.openstreetmap.org/'
);

$osm = new Services_OpenStreetMap($config);
$user = $osm->getUser();
$changeset = $osm->createChangeset();
$changeset->begin("Added Acme Vets.");
// The latitude and longitude values here are intentionally invalid, see
// note above.
$lat = 182.8638729;
$lon = -188.1983611;
$node = $osm->createNode($lat, $lon, array(
    'name' => 'Acme Vets',
    'building' => 'yes',
    'amenity' => 'vet')
);
$changeset->add($node);
$changeset->commit();

echo 'My OSM Mugshot is at ', $user->getImage(), "\n";

// vim:set et ts=4 sw=4:
?>
