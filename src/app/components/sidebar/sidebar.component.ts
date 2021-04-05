import { Component, OnInit } from '@angular/core';

declare const $: any;
declare interface RouteInfo {
  path: string;
  title: string;
  icon: string;
  class: string;
  children: ChildRouteInfo[];
}
declare interface ChildRouteInfo {
  path: string;
  title: string;
  icon: string;
  class: string;
}
export const ROUTES: RouteInfo[] = [
  { 
    path: '/dashboard', title: 'Dashboard', icon: 'dashboard', class: '', children: [] },
    {
      path: '/property', title: 'Manage Property', icon: 'business', class: '',
      children: [
        { path: '/submit-listing/new', title: 'Add Property', icon: 'playlist_add', class: '' },
        { path: '/property-list', title: 'Edit Property', icon: 'edit_location', class: '' },
        { path: '/inactive', title: 'Active / Inactive Property', icon: 'phonelink_off', class: '' },
      ]
    },
    {
      path: '/lead', title: 'Lead Management', icon: 'contact_phone', class: '',
      children: [
        { path: '/inquiry', title: 'Site Visit Request', icon: 'view_list', class: '' },
      ]
    },    
    { path: '/logout', title: 'Log Out', icon: 'input', class: '', children: [] },
];

export const ADMIN_ROUTES: RouteInfo[] = [
  { 
    path: '/dashboard', title: 'Dashboard', icon: 'dashboard', class: '', children: [] },
    {
      path: '/property', title: 'Manage Property', icon: 'business', class: '',
      children: [
        { path: '/verify', title: 'Verify Property', icon: 'verified_user', class: '' },
        { path: '/inactive', title: 'Inactive Property ', icon: 'phonelink_off', class: '' },
      ]
    },
    {
      path: '/customer', title: 'Customer Management', icon: 'business', class: '',
      children: [
        //{ path: '/registrations', title: 'Approve or Reject Users', icon: 'supervised_user_circle', class: '' },
        { path: '/users', title: 'Manage Users', icon: 'group', class: '' },
      ]
    },
    {
      path: '/reports', title: 'Reports', icon: 'assignment', class: '',
      children: [
        { path: '/reports/agent', title: 'Properties by Agent', icon: 'record_voice_over', class: '' },
        { path: '/reports/owner', title: 'Properties by Owner', icon: 'people', class: '' },
        { path: '/reports/developer', title: 'Properties by Developer', icon: 'perm_contact_calendar', class: '' },
        { path: '/reports/inquiry/agent', title: 'Site Visit Request by Agent', icon: 'record_voice_over', class: '' },
        { path: '/reports/inquiry/owner', title: 'Site Visit Request by Owner', icon: 'people', class: '' },
        { path: '/reports/inquiry/developer', title: 'Site Visit Request by Developer', icon: 'perm_contact_calendar', class: '' },
      ]
    },
    { path: '/logout', title: 'Log Out', icon: 'input', class: '', children: [] },
];

@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.css']
})
export class SidebarComponent implements OnInit {
  menuItems: any[];

  constructor() { }

  ngOnInit() {
    if(window.localStorage.getItem('has_role') === "ROLE_ADMIN") {
      this.menuItems = ADMIN_ROUTES.filter(menuItem => menuItem);
    } else if(window.localStorage.getItem('has_role') === "ROLE_USER") {
      this.menuItems = ROUTES.filter(menuItem => menuItem);
    }    
  }
  isMobileMenu() {
    if ($(window).width() > 991) {
      return false;
    }
    return true;
  };
}
