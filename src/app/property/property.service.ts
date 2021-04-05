import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { config } from 'config/config';
import 'rxjs/add/operator/shareReplay';
import 'rxjs/add/operator/do';
import { BasicModel } from './model/basic';
import { FloorModel } from './model/floor';
import { Amenities } from './model/amenities';
import { Nearby } from './model/nearby';
import { Gallery } from './model/gallery';
import { Material } from './model/material';
import { Banks } from './model/bank';

@Injectable({
  providedIn: 'root'
})
export class PropertyService {

  constructor(private http: HttpClient) { }

  getUserPropertyList(page: string, size: string) {
    let uri = '/property';
    if(page != null && size != null) {
      uri = uri + '?page=' + page + '&size=' + size;
    }
    return this.http.get<any>(config.API_URL + uri, { observe: 'response' })
      .shareReplay();
  }

  getBasicInfo(propId: number) {
    return this.http.get<BasicModel>(config.API_URL + '/property/basic/'+propId, { observe: 'response' })
      .shareReplay();
  }

  getFloorPlan(propId: number) {
    return this.http.get<FloorModel>(config.API_URL + '/property/floor/'+propId, { observe: 'response' })
      .shareReplay();
  }

  getGallery(propId: number) {
    return this.http.get<Gallery>(config.API_URL + '/property/gallery/'+propId, { observe: 'response' })
      .shareReplay();
  }

  getAmenities(propId: number) {
    return this.http.get<Amenities>(config.API_URL + '/property/amenities/'+propId, { observe: 'response' })
      .shareReplay();
  }

  getMaterials(propId: number) {
    return this.http.get<Material>(config.API_URL + '/property/material/'+propId, { observe: 'response' })
      .shareReplay();
  }

  getNearby(propId: number) {
    return this.http.get<Nearby>(config.API_URL + '/property/nearby/'+propId, { observe: 'response' })
      .shareReplay();
  }

  getBanks(propId: number) {
    return this.http.get<Banks>(config.API_URL + '/property/banks/'+propId, { observe: 'response' })
      .shareReplay();
  }

  processBasicInfo(request: any) {
    return this.http.post<BasicModel>(config.API_URL + '/property/basic', request, { observe: 'response' })
      .shareReplay();
  }

  processFloorInfo(request: any) {
    return this.http.post<FloorModel>(config.API_URL + '/property/floor', request, { observe: 'response' })
      .shareReplay();
  }

  processAmenities(request: any) {
    return this.http.post<Amenities>(config.API_URL + '/property/amenities', request, { observe: 'response' })
      .shareReplay();
  }

  processGallery(request: any) {
    return this.http.post<any>(config.API_URL + '/property/gallery', request, { observe: 'response' })
      .shareReplay();
  }

  processMaterialInfo(request: any) {
    return this.http.post<any>(config.API_URL + '/property/material', request, { observe: 'response' })
      .shareReplay();
  }
  processNearbyInfo(request: any) {
    return this.http.post<Nearby>(config.API_URL + '/property/nearby', request, { observe: 'response' })
      .shareReplay();
  }
  processSelectedBanks(request: any) {
    return this.http.post<any>(config.API_URL + '/property/banks', request, { observe: 'response' })
      .shareReplay();
  }

  verifyProprties(request: any) {
    return this.http.post<any>(config.API_URL + '/property/verify', request, { observe: 'response' })
      .shareReplay();
  }

  inactiveProprties(request: any) {
    return this.http.post<any>(config.API_URL + '/property/inactive', request, { observe: 'response' })
      .shareReplay();
  }

  getInquiryList(page: string, size: string) {
    let uri = '/property/inquiry';
    if(page != null && size != null) {
      uri = uri + '?page=' + page + '&size=' + size;
    }
    return this.http.get<any>(config.API_URL + uri, { observe: 'response' })
      .shareReplay();
  }

}
