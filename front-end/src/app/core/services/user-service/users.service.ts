import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { User } from '../../interfaces/user';
import { AuthService } from '../auth-service/auth.service';


@Injectable({
  providedIn: 'root'
})
export class UsersService {
  readonly url: string='http://localhost:8080/api/users';
  constructor(private httpClient:HttpClient) {}

  public getById(id:string): Observable<HttpResponse<User>>{
    return this.httpClient
      .get<User>(
        this.url+'/'+Number(id),
        {
          observe:'response'
        }
    );
  }

  public changeEmail(id:number, email:string):Observable<User>{
    return this.httpClient.put<User>(this.url+'/changeEmail/'+id+'/'+email,null);
  }

  public changeUsername(id:number, username:string):Observable<User>{
    return this.httpClient.put<User>(this.url+'/changeUsername/'+id+'/'+username,null);
  }

  public changePassword(id:number, oldPassword:string, newPassword:string):Observable<User>{
    return this.httpClient.put<User>(this.url+'/changePassword/'
      +id+'/'+oldPassword+'/'+newPassword,null);
  }

  public changeRole(id:number, role:string):Observable<User>{
    return this.httpClient.put<User>(this.url+'/changeRole/'+id+'/'+role,null);
  }

  public restore(id:number):Observable<User>{
    return this.httpClient.put<User>(this.url+'/restore/'+id,null);
  }

  public delete(id:number):Observable<User>{
    return this.httpClient.delete<User>(this.url+'/delete/'+id);
  }

  public confirmDelete(id:number):Observable<User>{
    return this.httpClient.delete<User>(this.url+'/delete/confirmed/'+id);
  }
}
