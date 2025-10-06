import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Register } from '../../interfaces/register';
import { User } from '../../interfaces/user';
import { LocalStorageService } from '../local-storage-service/local-storage.service';
import { AuthResponse } from '../../interfaces/auth-response';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  readonly url: string='http://localhost:8080/api/auth';
  constructor(private httpClient:HttpClient,
     private localStorageService:LocalStorageService) { }

  public register(user: Register):Observable<AuthResponse>{
    return this.httpClient.post<AuthResponse>(this.url+'/register',user);
  }

  public login(email:string, password:string):Observable<AuthResponse>{
    return this.httpClient.post<AuthResponse>(this.url+'/login',{
      email: email,
      password:password
    });
  }

  public setAuthToken(token:string):void{
    this.localStorageService.setItem("jwt",token)
  }

  public getAuthToken():string{
    return this.localStorageService.getItem("jwt")??"";
  }

  public getAuthHeaders(headers:HttpHeaders):HttpHeaders{
    return headers.set('Authorization','Bearer '+this.getAuthToken());
  }
}
