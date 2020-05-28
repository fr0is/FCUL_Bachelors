import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ClienteListaRouteComponent } from './cliente-lista-route.component';

describe('ClienteListaRouteComponent', () => {
  let component: ClienteListaRouteComponent;
  let fixture: ComponentFixture<ClienteListaRouteComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ClienteListaRouteComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ClienteListaRouteComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
