import { HttpClient, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ClassService {

  constructor(private httpClient:HttpClient) { }
  // public getAll():Observable<HttpResponse<>>{

  // }
}
